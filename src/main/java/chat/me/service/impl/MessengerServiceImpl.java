package chat.me.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chat.me.dao.impl.ChannelDaoImpl;
import chat.me.dao.impl.MessageDaoImpl;
import chat.me.dto.ChannelUserMstDto;
import chat.me.dto.MessageDeliveryStatusTrnDto;
import chat.me.dto.MessageTrnDto;
import chat.me.entity.ActiveUserStore;
import chat.me.entity.ChannelInfoEntity;
import chat.me.entity.MessageWithDeliverystatusInfoEntity;
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

	private final String DIRECT_MODE = "DIRECT";
	
	private final String CHANNEL_MODE = "CHANNEL";
	
	private String getMessageDeliveryStatusUsingSessionInfo(String receipientId) {
		return activeUserStore.getUserIds().contains(receipientId)?
				MESSAGE_UNREAD : MESSAGE_SENT;
	}
	
	private List<MessageDeliveryStatusTrnDto> 
			getMessageDeilveryStatusDtoFromMessageTrnDto(MessageTrnDto trnDto){
		List<MessageDeliveryStatusTrnDto> ret = new ArrayList<>();
		if(trnDto.getMessageMode().equals(DIRECT_MODE)) {
			//sourceId
			MessageDeliveryStatusTrnDto dto = new MessageDeliveryStatusTrnDto();
			dto.setMessageId(trnDto.getMessageId());
			dto.setUserId(trnDto.getSourceId());
			dto.setMessageDeliveryStatus(MESSAGE_READ);
			ret.add(dto);
			//destinationId
			dto = new MessageDeliveryStatusTrnDto();
			dto.setMessageId(trnDto.getMessageId());
			dto.setUserId(trnDto.getDestinationId());
			dto.setMessageDeliveryStatus(
					getMessageDeliveryStatusUsingSessionInfo(
							trnDto.getDestinationId()));
			ret.add(dto);
		}
		else {
			//all registered users for this channel
			String channelId = trnDto.getDestinationId();
			channelDaoImpl.getAllUserInfoByChannelId(
					channelId).stream().forEach(
						channelDto->{
							MessageDeliveryStatusTrnDto dto = new MessageDeliveryStatusTrnDto();
							dto.setMessageId(trnDto.getMessageId());
							dto.setUserId(channelDto.getUserId());
							dto.setMessageDeliveryStatus(
									channelDto.getUserId().equals(trnDto.getSourceId())?
											MESSAGE_READ:
											getMessageDeliveryStatusUsingSessionInfo(
													channelDto.getUserId())
									);
							ret.add(dto);
						}
					);
		}
		return ret;
	}
	
	@Override
	public MessageWithDeliverystatusInfoEntity saveMessage(MessageTrnDto dto) {
		MessageTrnDto messageDto = new MessageTrnDto();
		MessageWithDeliverystatusInfoEntity entity = new MessageWithDeliverystatusInfoEntity();
		dto.setMessageId(UUID.randomUUID().toString());
		dto.setMessageOperationStatus("CREATE");
		messageDto = messageDaoImpl.saveNewMessage(dto,
				getMessageDeilveryStatusDtoFromMessageTrnDto(dto));
		entity.setDestinationId(messageDto.getDestinationId());
		entity.setMessage(messageDto.getMessage());
		entity.setMessageCreationTime(messageDto.getMessageCreationTime());
		entity.setMessageDeliveryStatus(
			messageDto.getMessageMode().equals(
				DIRECT_MODE) ? getMessageDeliveryStatusUsingSessionInfo(dto.getDestinationId()):MESSAGE_UNREAD);
		entity.setMessageId(messageDto.getMessageId());
		entity.setMessageMode(messageDto.getMessageMode());
		entity.setMessageOperationStatus(messageDto.getMessageOperationStatus());
		entity.setSourceId(messageDto.getSourceId());
		return entity;
	}
	
	@Override
	public List<MessageWithDeliverystatusInfoEntity> saveMessage(List<MessageTrnDto> dtoList) {
		if(dtoList == null)
			return null;
		return dtoList.stream().map(
				dto->saveMessage(dto)).collect(Collectors.toList());
	}
	

	@Override
	public List<MessageWithDeliverystatusInfoEntity> fetchAllMessageBySourceAndDest(MessageTrnDto dto) {
		return messageDaoImpl.getBySourceAndDest(dto);
	}
	
	private List<String> getAllDestinationIds(String userId){
		List<String> destinationIds = new ArrayList<>();
		destinationIds.add(userId);
		destinationIds.addAll(channelDaoImpl.getAllChannelInfoByUserId(userId).stream()
				.map(entity->{
					return entity.getChannelMstDto().getChannelId();}).collect(Collectors.toList()));
		return destinationIds;
	}

	@Override
	public List<MessageTrnDto> markSentMessageAsUnread(String userId) {
		return messageDaoImpl.updateMessageDeliveryStatus(userId, MESSAGE_UNREAD);
	}

	@Override
	public List<MessageTrnDto> fetchAllMessageByDestAndDeliveryStatus(String userId, String deliveryStatus) {
		return messageDaoImpl.getByDestinationIdAndDeliveryStatus(userId, MESSAGE_UNREAD);
	}

	@Override
	public List<MessageTrnDto> markAllMessageAsReadByMessageIds(List<String> messageIds, String userId){
		return messageDaoImpl.updateMessageDeliveryStatus(messageIds, userId, MESSAGE_READ);
	}

	@Override
	public List<MessageTrnDto> fetchAllMessageByDestAndDeliveryStatus(List<String> destinationIds,
			String deliveryStatus) {
		return messageDaoImpl.getByDestinationIdAndDeliveryStatus(destinationIds, "", deliveryStatus);
	}

	@Override
	public List<MessageTrnDto> fetchAllUnreadMessage(String userId) {
		return messageDaoImpl.getAllNotReadByUserId(userId);
	}
	
}
