package chat.me.dao.spec;

import java.util.UUID;

public interface MessageUserDao {
	
	void saveMessageByMessageIdAndUserName(String messageId, String username);

}
