package chat.me.dao.spec;

import java.util.List;

import chat.me.dto.MessageTrnDto;
import chat.me.entity.MessageinfoEntity;

public interface MessageDao {

	MessageTrnDto updateMessageDeliveryStatus(String messageId, String deliveryStatus);
	
	MessageTrnDto saveMessageByUsername(String message, String username, String deliveryStatus);
	
	
	List<MessageinfoEntity> fetchAllMessage(String fromUsername, String toUsername);
}
