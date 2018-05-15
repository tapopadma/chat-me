package chat.me.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chat.me.dao.impl.ChannelDaoImpl;
import chat.me.dao.impl.MessageChannelDaoImpl;
import chat.me.dao.impl.MessageDaoImpl;
import chat.me.dao.impl.MessageUserDaoImpl;
import chat.me.dto.MessageChannelTrnDto;
import chat.me.dto.MessageTrnDto;
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
	
	@Override
	public MessageinfoEntity saveMessage(MessageinfoEntity entity) {
		if(entity == null)
			return null;
		MessageTrnDto messageDto = messageDaoImpl.saveMessageByUsername(entity.getMessage(), 
				entity.getFromUsername());
		messageUserDaoImpl.saveMessageByMessageIdAndUserName(messageDto.getMessageId(), 
				entity.getFromUsername());
		messageUserDaoImpl.saveMessageByMessageIdAndUserName(messageDto.getMessageId(), 
				entity.getToUsername());
		MessageinfoEntity res = new MessageinfoEntity();
		res.setFromUsername(entity.getFromUsername());
		res.setLastUpdated(messageDto.getLastUpdated());
		res.setMessage(messageDto.getMessage());
		res.setMessageId(messageDto.getMessageId());
		res.setToUsername(entity.getToUsername());
		return res;
	}
	
	@Override
	public MessageinfoEntity saveMessage(MessageinfoEntity entity,
			boolean isUserTyping) {
		if(isUserTyping)
			return entity;
		return saveMessage(entity); 
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
				entity.getUsername());
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
	
}
