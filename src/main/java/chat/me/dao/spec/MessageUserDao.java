package chat.me.dao.spec;

import java.util.List;

import chat.me.dto.MessageUsersTrnDto;

public interface MessageUserDao {
	
	void saveMessageByMessageIdAndUserName(String messageId, String username);
	
	List<MessageUsersTrnDto> getByUsername(String username);

}
