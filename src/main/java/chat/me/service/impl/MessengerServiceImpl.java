package chat.me.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import chat.me.dao.spec.ChannelDao;
import chat.me.dao.spec.MessageDao;
import chat.me.dto.MessageDeliveryStatusTrnDto;
import chat.me.dto.MessageTrnDto;
import chat.me.entity.ActiveUserStore;
import chat.me.entity.MessageWithDeliverystatusInfoEntity;
import chat.me.service.spec.MessengerService;

@Service
@CacheConfig(cacheNames= {"chat_me_cache"})
public class MessengerServiceImpl implements MessengerService{

	@Autowired
	private MessageDao messageDao;
	@Autowired
	private ChannelDao channelDao;
	@Autowired
	private ActiveUserStore activeUserStore;
	@Autowired	
	private CacheManager cacheManager;
	
	private final String MESSAGE_SENT = "SENT";
	private final String MESSAGE_UNREAD = "UNREAD";
	private final String MESSAGE_READ = "READ";

	private final String DIRECT_MODE = "DIRECT";
	
	private final String CHANNEL_MODE = "CHANNEL";
	
	private String getMessageDeliveryStatusUsingSessionInfo(String receipientId) {
		return activeUserStore.getUserIds().contains(receipientId)?
				MESSAGE_UNREAD : MESSAGE_SENT;
	}
	
	private List<MessageDeliveryStatusTrnDto> 
			getMessageDeilveryStatusDtoFromMessageTrnDto(MessageTrnDto trnDto){
		List<MessageDeliveryStatusTrnDto> ret = new ArrayList<>();
		if(trnDto.getMessageMode().equals(DIRECT_MODE)) {
			//sourceId
			MessageDeliveryStatusTrnDto dto = new MessageDeliveryStatusTrnDto();
			dto.setMessageId(trnDto.getMessageId());
			dto.setUserId(trnDto.getSourceId());
			dto.setMessageDeliveryStatus(MESSAGE_READ);
			ret.add(dto);
			//destinationId
			dto = new MessageDeliveryStatusTrnDto();
			dto.setMessageId(trnDto.getMessageId());
			dto.setUserId(trnDto.getDestinationId());
			dto.setMessageDeliveryStatus(
					getMessageDeliveryStatusUsingSessionInfo(
							trnDto.getDestinationId()));
			ret.add(dto);
		}
		else {
			//all registered users for this channel
			String channelId = trnDto.getDestinationId();
			channelDao.getAllUserInfoByChannelId(
					channelId).stream().forEach(
						channelDto->{
							MessageDeliveryStatusTrnDto dto = new MessageDeliveryStatusTrnDto();
							dto.setMessageId(trnDto.getMessageId());
							dto.setUserId(channelDto.getUserId());
							dto.setMessageDeliveryStatus(
									channelDto.getUserId().equals(trnDto.getSourceId())?
											MESSAGE_READ:
											getMessageDeliveryStatusUsingSessionInfo(
													channelDto.getUserId())
									);
							ret.add(dto);
						}
					);
		}
		return ret;
	}
	
	@Override
	public MessageWithDeliverystatusInfoEntity saveMessage(MessageTrnDto dto) {
		MessageTrnDto messageDto = new MessageTrnDto();
		MessageWithDeliverystatusInfoEntity entity = new MessageWithDeliverystatusInfoEntity();
		dto.setMessageId(UUID.randomUUID().toString());
		dto.setMessageOperationStatus("CREATE");
		messageDto = messageDao.saveNewMessage(dto,
				getMessageDeilveryStatusDtoFromMessageTrnDto(dto));
		entity.setDestinationId(messageDto.getDestinationId());
		entity.setMessage(messageDto.getMessage());
		entity.setMessageCreationTime(messageDto.getMessageCreationTime());
		entity.setMessageDeliveryStatus(
			messageDto.getMessageMode().equals(
				DIRECT_MODE) ? getMessageDeliveryStatusUsingSessionInfo(dto.getDestinationId()):MESSAGE_UNREAD);
		entity.setMessageId(messageDto.getMessageId());
		entity.setMessageMode(messageDto.getMessageMode());
		entity.setMessageOperationStatus(messageDto.getMessageOperationStatus());
		entity.setSourceId(messageDto.getSourceId());
		return entity;
	}
	
	@Override
	public List<MessageWithDeliverystatusInfoEntity> saveMessage(List<MessageTrnDto> dtoList) {
		if(dtoList == null)
			return null;
		return dtoList.stream().map(
				dto->saveMessage(dto)).collect(Collectors.toList());
	}
	

	@Override
	//@Cacheable(value="chat_me_cache", keyGenerator="customCacheKeyGenerator")
	public List<MessageWithDeliverystatusInfoEntity> fetchAllMessageBySourceAndDest(MessageTrnDto dto) {
		return messageDao.getBySourceAndDest(dto);
	}
	
	private List<String> getAllDestinationIds(String userId){
		List<String> destinationIds = new ArrayList<>();
		destinationIds.add(userId);
		destinationIds.addAll(channelDao.getAllChannelInfoByUserId(userId).stream()
				.map(entity->{
					return entity.getChannelMstDto().getChannelId();}).collect(Collectors.toList()));
		return destinationIds;
	}

	@Override
	public List<MessageTrnDto> markSentMessageAsUnread(String userId) {
		return messageDao.updateMessageDeliveryStatus(userId, MESSAGE_UNREAD);
	}

	@Override
	public List<MessageTrnDto> fetchAllMessageByDestAndDeliveryStatus(String userId, String deliveryStatus) {
		return messageDao.getByDestinationIdAndDeliveryStatus(userId, MESSAGE_UNREAD);
	}

	@Override
	public List<MessageTrnDto> markAllMessageAsReadByMessageIds(List<String> messageIds, String userId){
		return messageDao.updateMessageDeliveryStatus(messageIds, userId, MESSAGE_READ);
	}

	@Override
	public List<MessageTrnDto> fetchAllMessageByDestAndDeliveryStatus(List<String> destinationIds,
			String deliveryStatus) {
		return messageDao.getByDestinationIdAndDeliveryStatus(destinationIds, "", deliveryStatus);
	}

	@Override
	public List<MessageTrnDto> fetchAllUnreadMessage(String userId) {
		return messageDao.getAllNotReadByUserId(userId);
	}

	@Override
	public String deleteMessageByMessageId(String messageId) {
		messageDao.deleteMessageById(messageId);
		return messageId;
	}
	
}
