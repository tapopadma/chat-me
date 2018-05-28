package chat.me.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import chat.me.dto.MessageTrnDto;
import chat.me.entity.MessageTrnInfoEntity;
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
	public List<MessageTrnDto> fetchAllMessageBySourceAndDest(@RequestBody MessageTrnDto dto) {
		return messengerServiceImpl.fetchAllMessageBySourceAndDest(dto);
	}
	
	@MessageMapping("/chat")
	@SendTo("/topic/message")
	public SocketMessageEntity sendMessage(SocketMessageEntity entity) {
		if(entity.getMessageTrnInfoEntity() != null &&
				entity.getMessageTrnInfoEntity().getMessageTrnDtoList() != null
				&& !entity.getMessageTrnInfoEntity().getMessageTrnDtoList().isEmpty()) {
			if(entity.getMessageTrnInfoEntity().isMarkAllMessageAsRead()) {
				List<String> messageIds = entity.getMessageTrnInfoEntity().getMessageTrnDtoList()
						.stream().map(MessageTrnDto::getMessageId).collect(Collectors.toList());
				entity.getMessageTrnInfoEntity().setMessageTrnDtoList(
						messengerServiceImpl.markAllMessageAsReadByMessageIds(messageIds));
			}
			else {
				// save the message to DB
				messengerServiceImpl.saveMessage(
					entity.getMessageTrnInfoEntity().getMessageTrnDtoList());				
			}
		}
		if(entity.getUserSessionInfoEntity()!= null && 
				entity.getUserSessionInfoEntity().isLoginRequest()) {
			// update all message status to unread if sent
			MessageTrnInfoEntity messageTrn = new MessageTrnInfoEntity();
			List<MessageTrnDto> messageTrnDtoList = new ArrayList<>();
			messageTrnDtoList.addAll(messengerServiceImpl.markSentMessageAsUnreadByDestinationId(
					entity.getUserSessionInfoEntity().getUserId()));			
			messageTrn.setMessageTrnDtoList(messageTrnDtoList);
			entity.setMessageTrnInfoEntity(messageTrn);
		}
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value="/fetchAllUnreadMessage", method=RequestMethod.POST)
	public List<MessageTrnDto> fetchAllUnreadMessage(@RequestBody String userId){
		return messengerServiceImpl.fetchAllUnreadMessage(userId);
	}
	
	@Deprecated
	@ResponseBody
	@RequestMapping(value="/markAllMessageAsRead", method=RequestMethod.POST)
	public List<MessageTrnDto> markAllMessageAsRead(@RequestBody MessageTrnDto dto){
		return messengerServiceImpl.markAllMessageAsReadBySourceAndDest(
				dto.getSourceId(), dto.getDestinationId());
	}
	
}
