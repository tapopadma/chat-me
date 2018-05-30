package chat.me.service.spec;

import java.util.List;

import chat.me.dto.MessageTrnDto;
import chat.me.entity.MessageWithDeliverystatusInfoEntity;

public interface MessengerService {

	MessageWithDeliverystatusInfoEntity saveMessage(MessageTrnDto dto);
	List<MessageWithDeliverystatusInfoEntity> fetchAllMessageBySourceAndDest(MessageTrnDto dto);
	List<MessageWithDeliverystatusInfoEntity> saveMessage(List<MessageTrnDto> dtoList);
	List<MessageTrnDto> markSentMessageAsUnread(String userId);
	List<MessageTrnDto> markAllMessageAsReadByMessageIds(List<String> messageIds, String userId);
	List<MessageTrnDto> fetchAllMessageByDestAndDeliveryStatus(String destinationId, String deliveryStatus);
	List<MessageTrnDto> fetchAllMessageByDestAndDeliveryStatus(List<String> destinationIds, String deliveryStatus);
	List<MessageTrnDto> fetchAllUnreadMessage(String userId);
}
