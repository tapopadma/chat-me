package chat.me.dao.spec;

import java.util.List;

import chat.me.dto.MessageTrnDto;
import chat.me.entity.MessageinfoEntity;

public interface MessageDao {

	MessageTrnDto saveMessageByUsername(String message, String username);
	
	
	List<MessageinfoEntity> fetchAllMessage(String fromUsername, String toUsername);
}
