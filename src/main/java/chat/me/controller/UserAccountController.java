package chat.me.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import chat.me.dto.AccountMstDto;
import chat.me.entity.ActiveUserStore;
import chat.me.entity.UserBasicInfoEntity;
import chat.me.service.impl.UserAccountServiceImpl;

@Controller
@RequestMapping("/user")
public class UserAccountController {
	
	@Autowired
	private UserAccountServiceImpl userAccountServiceImpl;

	@Autowired
	private ActiveUserStore activeUserStore;
	
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
	public List<UserBasicInfoEntity> getAllUsers(){
		List<UserBasicInfoEntity> res = new ArrayList<>();
		userAccountServiceImpl.getAllUsers().stream().forEach(
				dto -> {
					UserBasicInfoEntity entity = new UserBasicInfoEntity();
					entity.setAccountMstDto(dto);
					entity.setLoggedIn(activeUserStore.getUserList().contains(
							dto.getUsername()));
					res.add(entity);
				}
				);
		return res;
	}
	
}
