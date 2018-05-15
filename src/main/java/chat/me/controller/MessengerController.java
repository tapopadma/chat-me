package chat.me.controller;

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

import chat.me.entity.ChannelmessageinfoEntity;
import chat.me.entity.MessageinfoEntity;
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
	public void saveMessage(@RequestBody MessageinfoEntity entity) {
		messengerServiceImpl.saveMessage(entity);
	}
	
	
	@ResponseBody
	@RequestMapping(value="getAllLoggedInUsers", method=RequestMethod.GET)
	public List<String> getAllLoggedInUsers(){
		return sessionRegistry.getAllPrincipals().stream()
				.filter(principal -> !sessionRegistry.getAllSessions(principal, false).isEmpty())
				.map(principal -> ((User)principal).getUsername())
				.collect(Collectors.toList());
	}
	
	@ResponseBody
	@RequestMapping(value="/fetchAllMessage", method=RequestMethod.POST)
	public List<MessageinfoEntity> fetchAllMessage(@RequestBody MessageinfoEntity entity) {
		return messengerServiceImpl.fetchAllMessage(entity.getFromUsername(), entity.getToUsername());
	}
	
	@ResponseBody
	@RequestMapping(value="/fetchAllChannelMessage", method=RequestMethod.POST)
	public List<ChannelmessageinfoEntity> fetchAllChannelMessage(
			@RequestBody ChannelmessageinfoEntity entity) {
		return messengerServiceImpl.fetchAllChannelMessage(entity.getChannelName());
	}
	
	@MessageMapping("/chat")
	@SendTo("/topic/message")
	public SocketMessageEntity sendMessage(SocketMessageEntity entity) {
		SocketMessageEntity res = new SocketMessageEntity();
		res.setIsUsertyping(entity.getIsUsertyping());
		res.setChannelmessageinfoEntity(messengerServiceImpl.saveMessage(
				entity.getChannelmessageinfoEntity(), entity.getIsUsertyping()));
		res.setmessageinfoEntity(messengerServiceImpl.saveMessage(
				entity.getmessageinfoEntity(), entity.getIsUsertyping()));
		res.setSessioninfoEntity(entity.getSessioninfoEntity());
		return res;
	}
	
}
