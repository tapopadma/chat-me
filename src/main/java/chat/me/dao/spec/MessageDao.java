package chat.me.dao.spec;

import java.util.List;

import chat.me.entity.MessageInfoEntity;

public interface MessageDao {

	String saveMessageByUsername(String message, String username);
	
	List<MessageInfoEntity> fetchAllMessage(String fromUsername, String toUsername);
}
