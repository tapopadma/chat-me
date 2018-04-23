package chat.me.controller;

import java.util.List;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import chat.me.dto.AccountMstDto;
import chat.me.entity.ActiveUserEntity;
import chat.me.service.impl.UserAccountServiceImpl;

@Controller
@RequestMapping("/user")
public class UserAccountController {
	
	@Autowired
	private UserAccountServiceImpl userAccountServiceImpl;
	
	@Autowired
	private ActiveUserEntity activeUserEntity;
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.GET, value="/getLoggedInUser")
	public AccountMstDto getAuthentication() {
		AccountMstDto entity = new AccountMstDto();
		entity.setUsername(
				SecurityContextHolder.getContext().getAuthentication().getName());
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.GET, value="/getAllUsers")
	public List<AccountMstDto> getAllUsers(){
		activeUserEntity.getUsers().add("val");
		return userAccountServiceImpl.getAllUsers();
	}
	
}
