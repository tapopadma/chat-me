package chat.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import chat.me.entity.UserAccountEntity;
import chat.me.service.impl.UserAccountServiceImpl;


@Controller
@RequestMapping("/signup")
public class SignupController {
	
	@Autowired
	private UserAccountServiceImpl userAccountServiceImpl;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public UserAccountEntity saveUserData(@RequestBody UserAccountEntity entity) {
		return userAccountServiceImpl.saveUserAccountData(entity);
	}
}
