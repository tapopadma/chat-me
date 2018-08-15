package chat.me.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import chat.me.dao.spec.ChannelDao;
import chat.me.dao.spec.UserAccountDao;
import chat.me.dto.MessageTrnDto;
import chat.me.entity.MessageTrnInfoEntity;
import chat.me.entity.MessageWithDeliverystatusInfoEntity;
import chat.me.entity.SocketMessageEntity;
import chat.me.service.spec.MessengerService;

@Controller
@RequestMapping("/messenger")
public class MessengerController {

	@Autowired
	private MessengerService messengerService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	@Qualifier("userAccountDaoMySQLImpl")
	private UserAccountDao userAccountDao;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Deprecated
	@ResponseBody
	@RequestMapping(value="/saveMessage", method=RequestMethod.POST)
	public void saveMessage(@RequestBody MessageTrnDto dto) {
		messengerService.saveMessage(dto);
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getAllLoggedInUsers", method=RequestMethod.GET)
	public List<String> getAllLoggedInUsers(){
		return sessionRegistry.getAllPrincipals().stream()
				.filter(principal -> !sessionRegistry.getAllSessions(principal, false).isEmpty())
				.map(principal -> ((User)principal).getUsername())
				.collect(Collectors.toList());
	}
	
	@ResponseBody
	@RequestMapping(value="/fetchAllMessageBySourceAndDest", method=RequestMethod.POST)
	public List<MessageWithDeliverystatusInfoEntity> fetchAllMessageBySourceAndDest(
			@RequestBody MessageTrnDto dto) {
		return messengerService.fetchAllMessageBySourceAndDest(dto);
	}
	
	
	@MessageMapping("/chat/userSessionInfo/{userId}")
	public void sendUserSessionInfoMessage(@DestinationVariable String userId,
			SocketMessageEntity entity) {
		//userSessionInfoEntity
		if(entity.getUserSessionInfoEntity()!= null && 
				entity.getUserSessionInfoEntity().isLoginRequest()) {
			// update all message status to unread if sent
			MessageTrnInfoEntity messageTrn = new MessageTrnInfoEntity();
			List<MessageTrnDto> messageTrnDtoList = messengerService.markSentMessageAsUnread(
					entity.getUserSessionInfoEntity().getUserId());
			messageTrn.setMessageTrnDtoList(messageTrnDtoList.stream().map(dto->{
				return MessageTrnDto.toMessageWithDeliverystatusInfoEntity(dto, "UNREAD");
			}).collect(Collectors.toList()));
			entity.setMessageTrnInfoEntity(messageTrn);
		}
		userAccountDao.getAllUserAccounts().stream()
			.filter(dto->!dto.getUserId().equals(userId)).forEach(dto->{
			simpMessagingTemplate.convertAndSend("/topic/message/" + dto.getUserId(), entity);
		});
	}
	
	@MessageMapping("/chat/messageOperationInfo/{sourceId}/{destinationId}/{messageMode}")
	public void sendMessageOperationInfoMessage(
			@DestinationVariable String sourceId,
			@DestinationVariable String destinationId,
			@DestinationVariable String messageMode,
			SocketMessageEntity entity) {
		if(entity.getMessageOperationEntity() != null) {
			String operation = entity.getMessageOperationEntity().getOperation();
			String messageId = entity.getMessageOperationEntity().getMessageId();
			switch(operation) {
				case "DELETE":
					messengerService.deleteMessageByMessageId(messageId);
					break;
				default:
					break;
			}
		}
		sendSocketMessageByMessageMode(sourceId, destinationId, messageMode, entity);
	}
	
	@MessageMapping("/chat/messageMarkAsReadInfo/{sourceId}/{destinationId}/{messageMode}")
	public void sendMessageMarkAsReadInfoMessage(
			@DestinationVariable String sourceId,
			@DestinationVariable String destinationId,
			@DestinationVariable String messageMode,
			SocketMessageEntity entity) {

		//messageMarkAsReadInfoEntity
		if(entity.getMessageMarkAsReadInfoEntity() != null &&
				entity.getMessageMarkAsReadInfoEntity().getMessageIds() != null
				&& !entity.getMessageMarkAsReadInfoEntity().getMessageIds().isEmpty()) {
			//mark all messageIds of userId as READ
			
			List<MessageWithDeliverystatusInfoEntity> messageTrnDtoList = 
					messengerService.markAllMessageAsReadByMessageIds(
							entity.getMessageMarkAsReadInfoEntity().getMessageIds(),
							entity.getMessageMarkAsReadInfoEntity().getUserId())
					.stream().map(dto->{
						return MessageTrnDto.toMessageWithDeliverystatusInfoEntity(dto, "READ");
					}).collect(Collectors.toList());
			MessageTrnInfoEntity entity1 = new MessageTrnInfoEntity();
			entity1.setMessageTrnDtoList(messageTrnDtoList);
			entity.setMessageTrnInfoEntity(entity1);
		}
		sendSocketMessageByMessageMode(sourceId, destinationId, messageMode, entity);
	}
	
	private void sendSocketMessageByMessageMode(
			String sourceId, String destinationId, 
			String messageMode, SocketMessageEntity entity) {
		if(messageMode.equals("CHANNEL")) {
			String channelId = destinationId;
			channelDao.getAllUserInfoByChannelId(channelId).stream()
				.forEach(dto->{
					simpMessagingTemplate.convertAndSend("/topic/message/" + dto.getUserId(),
							entity);
				});
		}
		else {
			simpMessagingTemplate.convertAndSend("/topic/message/"+destinationId, entity);
			simpMessagingTemplate.convertAndSend("/topic/message/"+sourceId, entity);
		}
	}

	@MessageMapping("/chat/messageTypingInfo/{sourceId}/{destinationId}/{messageMode}")
	public void sendMessageTypingInfoMessage(
			@DestinationVariable String sourceId,
			@DestinationVariable String destinationId,
			@DestinationVariable String messageMode,
			SocketMessageEntity entity) {
		if(entity.getMessageTypingInfoEntity() != null
				&& entity.getMessageTypingInfoEntity().getMessageTrnDto() != null) {
			
		}
		sendSocketMessageByMessageMode(sourceId, destinationId, messageMode, entity);
	}
	
	
	@MessageMapping("/chat/messageTrnInfo/{sourceId}/{destinationId}/{messageMode}")
	public void sendMessageTrnInfoMessage(
			@DestinationVariable String sourceId,
			@DestinationVariable String destinationId,
			@DestinationVariable String messageMode,
			SocketMessageEntity entity) {
		
		//messageTrnInfoEntity
		if(entity.getMessageTrnInfoEntity() != null &&
				entity.getMessageTrnInfoEntity().getMessageTrnDtoList() != null
				&& !entity.getMessageTrnInfoEntity().getMessageTrnDtoList().isEmpty()) {
			
			List<MessageTrnDto> messageTrnDtoList = entity.getMessageTrnInfoEntity()
					.getMessageTrnDtoList().stream()
					.map(MessageWithDeliverystatusInfoEntity::toMessageTrnDto)
					.collect(Collectors.toList());
			
			// save the message to DB
			List<MessageWithDeliverystatusInfoEntity> entityList = messengerService.saveMessage(
					messageTrnDtoList);
			MessageTrnInfoEntity entity1 = new MessageTrnInfoEntity();
			entity1.setMessageTrnDtoList(entityList);
			entity.setMessageTrnInfoEntity(entity1);
		}
		sendSocketMessageByMessageMode(sourceId, destinationId, messageMode, entity);
	}
	
	@ResponseBody
	@RequestMapping(value="/fetchAllUnreadMessage", method=RequestMethod.POST)
	public List<MessageTrnDto> fetchAllUnreadMessage(@RequestBody String userId){
		return messengerService.fetchAllUnreadMessage(userId);
	}
	
	@RequestMapping(value="/deleteMessage/{messageId}")
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void deleteMessage(@PathVariable("messageId") String messageId) {
		messengerService.deleteMessageByMessageId(messageId);
	}
	
}
