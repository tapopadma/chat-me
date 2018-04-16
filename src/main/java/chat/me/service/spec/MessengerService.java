package chat.me.service.spec;

import chat.me.entity.MessageInfoEntity;

public interface MessengerService {

	void saveMessage(MessageInfoEntity entity);
}
