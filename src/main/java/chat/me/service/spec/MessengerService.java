package chat.me.service.spec;

import java.util.List;

import chat.me.entity.MessageinfoEntity;

public interface MessengerService {

	MessageinfoEntity saveMessage(MessageinfoEntity entity);
	List<MessageinfoEntity> fetchAllMessage(String fromUsername, String toUsername);
}
