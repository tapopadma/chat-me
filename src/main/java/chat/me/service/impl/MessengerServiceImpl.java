package chat.me.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chat.me.dao.impl.MessageDaoImpl;
import chat.me.dao.impl.MessageUserDaoImpl;
import chat.me.dto.MessageTrnDto;
import chat.me.entity.MessageInfoEntity;
import chat.me.service.spec.MessengerService;

@Service
public class MessengerServiceImpl implements MessengerService{

	@Autowired
	private MessageDaoImpl messageDaoImpl;
	@Autowired
	private MessageUserDaoImpl messageUserDaoImpl;
	
	@Override
	public MessageInfoEntity saveMessage(MessageInfoEntity entity) {
		if(entity.getIsUsertyping()) {
			return entity;
		}
		MessageTrnDto messageDto = messageDaoImpl.saveMessageByUsername(entity.getMessage(), entity.getFromUsername());
		messageUserDaoImpl.saveMessageByMessageIdAndUserName(messageDto.getMessageId(), entity.getFromUsername());
		messageUserDaoImpl.saveMessageByMessageIdAndUserName(messageDto.getMessageId(), entity.getToUsername());
		MessageInfoEntity res = new MessageInfoEntity();
		res.setFromUsername(entity.getFromUsername());
		res.setLastUpdated(messageDto.getLastUpdated());
		res.setMessage(messageDto.getMessage());
		res.setMessageId(messageDto.getMessageId());
		res.setToUsername(entity.getToUsername());
		return res;
	}

	@Override
	public List<MessageInfoEntity> fetchAllMessage(String fromUsername, String toUsername) {
		return messageDaoImpl.fetchAllMessage(fromUsername, toUsername);
	}

	
}
