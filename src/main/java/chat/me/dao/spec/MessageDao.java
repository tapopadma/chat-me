package chat.me.dao.spec;

import java.util.List;

import chat.me.dto.MessageDeliveryStatusTrnDto;
import chat.me.dto.MessageTrnDto;
import chat.me.entity.MessageWithDeliverystatusInfoEntity;

public interface MessageDao {

	
	List<MessageTrnDto> updateMessageDeliveryStatus(String userId, String deliveryStatus);
	
	MessageTrnDto saveNewMessage(MessageTrnDto dto, List<MessageDeliveryStatusTrnDto> dtoList);
	
	List<MessageTrnDto> getByDestinationIdAndDeliveryStatus(String destinationId, String messageDeliveryStatus);
	
	List<MessageTrnDto> getByDestinationIdAndDeliveryStatus(List<String> destinationIds, 
			String userId, String messageDeliveryStatus);
	
	List<MessageWithDeliverystatusInfoEntity> getBySourceAndDest(MessageTrnDto dto);
	
	List<MessageTrnDto> getAllNotReadBySourceAndDest(
			String sourceId, String destinationId);
	
	List<MessageTrnDto> getAllNotReadByDestIds(List<String> destinationIds, String userId);
	
	List<MessageTrnDto> getAllNotReadByUserId(String userId);
}
