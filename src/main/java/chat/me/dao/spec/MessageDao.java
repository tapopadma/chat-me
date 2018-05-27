package chat.me.dao.spec;

import java.util.List;

import chat.me.dto.MessageTrnDto;

public interface MessageDao {

	
	List<MessageTrnDto> updateMessageDeliveryStatus(List<String> messageIds, String deliveryStatus);
	
	MessageTrnDto saveNewMessage(MessageTrnDto dto);
	
	List<MessageTrnDto> getByDestinationIdAndDeliveryStatus(String userId, String messageDeliveryStatus);
	
	List<MessageTrnDto> getBySourceAndDest(MessageTrnDto dto);
	
	List<MessageTrnDto> getAllNotReadBySourceAndDest(String sourceId, String destinationId);
	
}
