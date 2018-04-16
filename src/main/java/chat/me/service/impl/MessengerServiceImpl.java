package chat.me.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chat.me.dao.impl.MessageDaoImpl;
import chat.me.dao.impl.MessageUserDaoImpl;
import chat.me.entity.MessageInfoEntity;
import chat.me.service.spec.MessengerService;

@Service
public class MessengerServiceImpl implements MessengerService{

	@Autowired
	private MessageDaoImpl messageDaoImpl;
	@Autowired
	private MessageUserDaoImpl messageUserDaoImpl;
	
	@Override
	public void saveMessage(MessageInfoEntity entity) {
		String messageId = messageDaoImpl.saveMessageByUsername(entity.getMessage(), entity.getFromUsername());
		messageUserDaoImpl.saveMessageByMessageIdAndUserName(messageId, entity.getFromUsername());
		messageUserDaoImpl.saveMessageByMessageIdAndUserName(messageId, entity.getToUsername());
	}

	
}
