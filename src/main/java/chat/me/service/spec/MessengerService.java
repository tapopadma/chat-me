package chat.me.service.spec;

import java.util.List;

import chat.me.entity.MessageInfoEntity;

public interface MessengerService {

	void saveMessage(MessageInfoEntity entity);
	List<MessageInfoEntity> fetchAllMessage(String fromUsername, String toUsername);
}
