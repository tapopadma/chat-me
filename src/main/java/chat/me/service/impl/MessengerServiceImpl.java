package chat.me.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chat.me.dao.impl.ChannelDaoImpl;
import chat.me.dao.impl.MessageDaoImpl;
import chat.me.dto.MessageTrnDto;
import chat.me.entity.ActiveUserStore;
import chat.me.entity.ChannelInfoEntity;
import chat.me.service.spec.MessengerService;

@Service
public class MessengerServiceImpl implements MessengerService{

	@Autowired
	private MessageDaoImpl messageDaoImpl;
	@Autowired
	private ChannelDaoImpl channelDaoImpl;
	@Autowired
	private ActiveUserStore activeUserStore;
	
	private final String MESSAGE_SENT = "SENT";
	private final String MESSAGE_UNREAD = "UNREAD";
	private final String MESSAGE_READ = "READ";
	
	private String getMessageDeliveryStatusUsingSessionInfo(String receipientId) {
		return activeUserStore.getUserIds().contains(receipientId)?
				MESSAGE_UNREAD : MESSAGE_SENT;
	}
	
	@Override
	public MessageTrnDto saveMessage(MessageTrnDto dto) {
		MessageTrnDto messageDto = new MessageTrnDto();
		dto.setMessageDeliveryStatus(getMessageDeliveryStatusUsingSessionInfo(
				dto.getDestinationId()));
		dto.setMessageOperationStatus("CREATE");
		messageDto = messageDaoImpl.saveNewMessage(dto);
		return messageDto;
	}
	
	@Override
	public List<MessageTrnDto> saveMessage(List<MessageTrnDto> dtoList) {
		if(dtoList == null)
			return null;
		return dtoList.stream().map(
				dto->saveMessage(dto)).collect(Collectors.toList());
	}
	

	@Override
	public List<MessageTrnDto> fetchAllMessageBySourceAndDest(MessageTrnDto dto) {
		return messageDaoImpl.getBySourceAndDest(dto);
	}

	@Override
	public List<MessageTrnDto> markSentMessageAsUnreadByDestinationId(String destinationId) {
		List<String> messageIds = messageDaoImpl.getByDestinationIdAndDeliveryStatus(
				destinationId, MESSAGE_SENT).stream().map(MessageTrnDto::getMessageId)
				.collect(Collectors.toList());
		return messageDaoImpl.updateMessageDeliveryStatus(messageIds, MESSAGE_UNREAD);
	}

	@Override
	public List<MessageTrnDto> fetchAllMessageByDestAndDeliveryStatus(String userId, String deliveryStatus) {
		return messageDaoImpl.getByDestinationIdAndDeliveryStatus(userId, MESSAGE_UNREAD);
	}

	@Override
	public List<MessageTrnDto> markAllMessageAsReadBySourceAndDest(String sourceId,
			String destinationId) {
		List<String> messageIds = messageDaoImpl.getAllNotReadBySourceAndDest(
				sourceId, destinationId).stream().map(MessageTrnDto::getMessageId)
				.collect(Collectors.toList());
		return messageDaoImpl.updateMessageDeliveryStatus(messageIds, MESSAGE_READ);
	}
	
	@Override
	public List<MessageTrnDto> markAllMessageAsReadByMessageIds(List<String> messageIds){
		return messageDaoImpl.updateMessageDeliveryStatus(messageIds, MESSAGE_READ);
	}

	@Override
	public List<MessageTrnDto> fetchAllMessageByDestAndDeliveryStatus(List<String> destinationIds,
			String deliveryStatus) {
		return messageDaoImpl.getByDestinationIdAndDeliveryStatus(destinationIds, deliveryStatus);
	}

	@Override
	public List<MessageTrnDto> fetchAllUnreadMessage(String userId) {
		List<String> destinationIds = new ArrayList<>();
		destinationIds.add(userId);
		destinationIds.addAll(channelDaoImpl.getAllChannelInfoByUserId(userId).stream()
				.map(ChannelInfoEntity::getChannelId).collect(Collectors.toList()));
		return fetchAllMessageByDestAndDeliveryStatus(destinationIds, MESSAGE_UNREAD);
	}
	
}
