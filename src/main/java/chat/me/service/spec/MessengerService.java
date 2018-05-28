package chat.me.service.spec;

import java.util.List;

import chat.me.dto.MessageTrnDto;

public interface MessengerService {

	MessageTrnDto saveMessage(MessageTrnDto dto);
	List<MessageTrnDto> fetchAllMessageBySourceAndDest(MessageTrnDto dto);
	List<MessageTrnDto> saveMessage(List<MessageTrnDto> dtoList);
	List<MessageTrnDto> markSentMessageAsUnreadByDestinationId(String userId);
	List<MessageTrnDto> markAllMessageAsReadByMessageIds(List<String> messageIds);
	List<MessageTrnDto> markAllMessageAsReadBySourceAndDest(String sourceId, String destinationId);
	List<MessageTrnDto> fetchAllMessageByDestAndDeliveryStatus(String destinationId, String deliveryStatus);
	List<MessageTrnDto> fetchAllMessageByDestAndDeliveryStatus(List<String> destinationIds, String deliveryStatus);
	List<MessageTrnDto> fetchAllUnreadMessage(String userId);
}
