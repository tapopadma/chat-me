package chat.me.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chat.me.dao.impl.ChannelDaoImpl;
import chat.me.dao.impl.MessageChannelDaoImpl;
import chat.me.dao.impl.MessageDaoImpl;
import chat.me.dao.impl.MessageUserDaoImpl;
import chat.me.dto.MessageChannelTrnDto;
import chat.me.dto.MessageTrnDto;
import chat.me.dto.MessageUsersTrnDto;
import chat.me.entity.ActiveUserStore;
import chat.me.entity.ChannelmessageinfoEntity;
import chat.me.entity.MessageinfoEntity;
import chat.me.service.spec.MessengerService;

@Service
public class MessengerServiceImpl implements MessengerService{

	@Autowired
	private MessageDaoImpl messageDaoImpl;
	@Autowired
	private MessageUserDaoImpl messageUserDaoImpl;
	@Autowired
	private MessageChannelDaoImpl messageChannelDaoImpl;
	@Autowired
	private ChannelDaoImpl channelDaoImpl;
	@Autowired
	private ActiveUserStore activeUserStore;
	
	private final String MESSAGE_SENT = "SENT";
	private final String MESSAGE_UNREAD = "UNREAD";
	private final String MESSAGE_READ = "READ";
	
	private String getMessageDeliveryStatusUsingSessionInfo(String receipientName) {
		return activeUserStore.getUserList().contains(receipientName)?
				MESSAGE_UNREAD : MESSAGE_SENT;
	}
	
	@Override
	public MessageinfoEntity saveMessage(MessageinfoEntity entity) {
		if(entity == null)
			return null;
		MessageTrnDto messageDto = new MessageTrnDto();
		if(entity.getDeliveryStatus() != null && 
				entity.getDeliveryStatus().equals(MESSAGE_READ)) {//Message already saved and needs deliveryStatus to 'READ'
			List<String> messageIds = new ArrayList<>();
			messageIds.add(entity.getMessageId());
			messageDto = messageDaoImpl.
					updateMessageDeliveryStatus(messageIds, MESSAGE_READ).get(0);
		}
		else {
			messageDto = messageDaoImpl.saveMessageByUsername(entity.getMessage(), 
					entity.getFromUsername(), getMessageDeliveryStatusUsingSessionInfo(
							entity.getToUsername()));
			messageUserDaoImpl.saveMessageByMessageIdAndUserName(messageDto.getMessageId(), 
					entity.getFromUsername());
			messageUserDaoImpl.saveMessageByMessageIdAndUserName(messageDto.getMessageId(), 
					entity.getToUsername());
		}
		MessageinfoEntity res = new MessageinfoEntity();
		res.setFromUsername(entity.getFromUsername());
		res.setLastUpdated(messageDto.getLastUpdated());
		res.setMessage(messageDto.getMessage());
		res.setMessageId(messageDto.getMessageId());
		res.setToUsername(entity.getToUsername());
		res.setDeliveryStatus(messageDto.getDeliveryStatus());
		return res;
	}
	
	@Override
	public List<MessageinfoEntity> saveMessage(List<MessageinfoEntity> entityList,
			boolean isUserTyping) {
		if(isUserTyping)
			return entityList;
		if(entityList == null)
			return null;
		return entityList.stream().map(
				entity->saveMessage(entity)).collect(Collectors.toList());
	}
	

	@Override
	public List<MessageinfoEntity> fetchAllMessage(String fromUsername, String toUsername) {
		return messageDaoImpl.fetchAllMessage(fromUsername, toUsername);
	}

	@Override
	public ChannelmessageinfoEntity saveMessage(ChannelmessageinfoEntity entity) {
		if(entity == null)
			return null;
		MessageTrnDto messageDto = messageDaoImpl.saveMessageByUsername(entity.getMessage(), 
				entity.getUsername(), MESSAGE_SENT);
		String channelId = channelDaoImpl.getChannelIdFromChannelName(entity.getChannelName());
		MessageChannelTrnDto messageChannelDto = new MessageChannelTrnDto(null,
				messageDto.getMessageId(), channelId);
		messageChannelDaoImpl.insert(messageChannelDto);
		return entity;
	}

	@Override
	public ChannelmessageinfoEntity saveMessage(ChannelmessageinfoEntity entity,
			boolean isUserTyping) {
		if(isUserTyping)
			return entity;
		return saveMessage(entity);
	}
	
	@Override
	public List<ChannelmessageinfoEntity> fetchAllChannelMessage(String channelName) {
		String channelId = channelDaoImpl.getChannelIdFromChannelName(channelName);
		return messageChannelDaoImpl.getAllMessageByChannel(channelId);
	}

	@Override
	public List<MessageinfoEntity> updateMessageDeliveryStatusByRecipientName(String toUsername) {
		List<String> allMessageIds = 
				messageUserDaoImpl.getByUsername(toUsername).stream().
				map(MessageUsersTrnDto::getMessageId).distinct().collect(Collectors.toList());
		return messageDaoImpl.updateMessageDeliveryStatus(
				allMessageIds, MESSAGE_UNREAD).stream()
				.map(dto->{
					MessageinfoEntity entity = new MessageinfoEntity();
					entity.setDeliveryStatus(dto.getDeliveryStatus());
					entity.setFromUsername(dto.getUsername());
					entity.setLastUpdated(dto.getLastUpdated());
					entity.setMessage(dto.getMessage());
					entity.setMessageId(dto.getMessageId());
					entity.setToUsername(toUsername);
					return entity;
				}).collect(Collectors.toList());
	}
	
}
