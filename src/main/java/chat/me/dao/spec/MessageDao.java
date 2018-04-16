package chat.me.dao.spec;

import java.util.UUID;

public interface MessageDao {

	String saveMessageByUsername(String message, String username);
}
