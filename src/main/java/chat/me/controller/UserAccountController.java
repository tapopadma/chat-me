package chat.me.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import chat.me.dto.UserAccountDto;
import chat.me.service.impl.UserAccountServiceImpl;

@Controller
@RequestMapping("/user")
public class UserAccountController {
	
	@Autowired
	private UserAccountServiceImpl userAccountServiceImpl;

	@ResponseBody
	@RequestMapping(method=RequestMethod.GET, value="/getLoggedInUser")
	public UserAccountDto getAuthentication() {
		UserAccountDto entity = new UserAccountDto();
		entity.setUsername(
				SecurityContextHolder.getContext().getAuthentication().getName());
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.GET, value="/getAllUsers")
	public List<UserAccountDto> getAllUsers(){
		return userAccountServiceImpl.getAllUsers();
	}
	
}
