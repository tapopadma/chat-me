package chat.me.controller;

import java.util.ArrayList;
import java.util.List;

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

import chat.me.entity.MessageInfoEntity;
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
	public void saveMessage(@RequestBody MessageInfoEntity entity) {
		messengerServiceImpl.saveMessage(entity);
	}
	
	@ResponseBody
	@RequestMapping(value="/fetchAllMessage", method=RequestMethod.POST)
	public List<MessageInfoEntity> fetchAllMessage(@RequestBody MessageInfoEntity entity) {
		return messengerServiceImpl.fetchAllMessage(entity.getFromUsername(), entity.getToUsername());
	}
	
	public boolean getLoggedInStatusByUsername(String username) {
		List<String> allUsers = getAllLoggedInUsers();
		return allUsers.contains(username);
	}
	
	private List<String> getAllLoggedInUsers(){
		List<String> allUsers = new ArrayList<>();
		List<Object> principals = sessionRegistry.getAllPrincipals();
		for(Object principal : principals) {
			if(principal instanceof User) {
				User user = (User) principal;
				allUsers.add(user.getUsername());
			}
		}
		return allUsers;
	}
	
	@MessageMapping("/chat")
	@SendTo("/topic/message")
	public MessageInfoEntity sendMessage(MessageInfoEntity entity) {
		entity.setUserloggedin(
				getLoggedInStatusByUsername(entity.getToUsername()));
		return messengerServiceImpl.saveMessage(entity);
	}
	
}
