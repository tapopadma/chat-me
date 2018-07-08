package chat.me.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import chat.me.dto.MessageTrnDto;
import chat.me.entity.MessageTrnInfoEntity;
import chat.me.entity.MessageWithDeliverystatusInfoEntity;
import chat.me.entity.SocketMessageEntity;
import chat.me.service.impl.MessengerServiceImpl;

@Controller
@RequestMapping("/messenger")
public class MessengerController {

	@Autowired
	private MessengerServiceImpl messengerServiceImpl;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Deprecated
	@ResponseBody
	@RequestMapping(value="/saveMessage", method=RequestMethod.POST)
	public void saveMessage(@RequestBody MessageTrnDto dto) {
		messengerServiceImpl.saveMessage(dto);
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
		return messengerServiceImpl.fetchAllMessageBySourceAndDest(dto);
	}
	
	@MessageMapping("/chat")
	@SendTo("/topic/message")
	public SocketMessageEntity sendMessage(SocketMessageEntity entity) {
		
		//messageTrnInfoEntity
		if(entity.getMessageTrnInfoEntity() != null &&
				entity.getMessageTrnInfoEntity().getMessageTrnDtoList() != null
				&& !entity.getMessageTrnInfoEntity().getMessageTrnDtoList().isEmpty()) {
			
			List<MessageTrnDto> messageTrnDtoList = entity.getMessageTrnInfoEntity()
					.getMessageTrnDtoList().stream()
					.map(MessageWithDeliverystatusInfoEntity::toMessageTrnDto)
					.collect(Collectors.toList());
			
			// save the message to DB
			List<MessageWithDeliverystatusInfoEntity> entityList = messengerServiceImpl.saveMessage(
					messageTrnDtoList);
			MessageTrnInfoEntity entity1 = new MessageTrnInfoEntity();
			entity1.setMessageTrnDtoList(entityList);
			entity.setMessageTrnInfoEntity(entity1);
		}
		
		//userSessionInfoEntity
		else if(entity.getUserSessionInfoEntity()!= null && 
				entity.getUserSessionInfoEntity().isLoginRequest()) {
			// update all message status to unread if sent
			MessageTrnInfoEntity messageTrn = new MessageTrnInfoEntity();
			List<MessageTrnDto> messageTrnDtoList = messengerServiceImpl.markSentMessageAsUnread(
					entity.getUserSessionInfoEntity().getUserId());
			messageTrn.setMessageTrnDtoList(messageTrnDtoList.stream().map(dto->{
				return MessageTrnDto.toMessageWithDeliverystatusInfoEntity(dto, "UNREAD");
			}).collect(Collectors.toList()));
			entity.setMessageTrnInfoEntity(messageTrn);
		}
		
		//messageMarkAsReadInfoEntity
		else if(entity.getMessageMarkAsReadInfoEntity() != null &&
				entity.getMessageMarkAsReadInfoEntity().getMessageIds() != null
				&& !entity.getMessageMarkAsReadInfoEntity().getMessageIds().isEmpty()) {
			//mark all messageIds of userId as READ
			
			List<MessageWithDeliverystatusInfoEntity> messageTrnDtoList = messengerServiceImpl.markAllMessageAsReadByMessageIds(
							entity.getMessageMarkAsReadInfoEntity().getMessageIds(),
							entity.getMessageMarkAsReadInfoEntity().getUserId())
					.stream().map(dto->{
						return MessageTrnDto.toMessageWithDeliverystatusInfoEntity(dto, "READ");
					}).collect(Collectors.toList());
			MessageTrnInfoEntity entity1 = new MessageTrnInfoEntity();
			entity1.setMessageTrnDtoList(messageTrnDtoList);
			entity.setMessageTrnInfoEntity(entity1);
		}
		
		//messageTypingInfoEntity
		else {
			//just broadcast via /topic/message
		}
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value="/fetchAllUnreadMessage", method=RequestMethod.POST)
	public List<MessageTrnDto> fetchAllUnreadMessage(@RequestBody String userId){
		return messengerServiceImpl.fetchAllUnreadMessage(userId);
	}
	
	@RequestMapping(value="/deleteMessage/{messageId}")
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void deleteMessage(@PathVariable("messageId") String messageId) {
		messengerServiceImpl.deleteMessageByMessageId(messageId);
	}
	
}
