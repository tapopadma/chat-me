package chat.me.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
}
