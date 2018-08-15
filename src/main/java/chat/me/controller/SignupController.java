package chat.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import chat.me.dto.UserMstDto;
import chat.me.service.spec.UserAccountService;


@Controller
@RequestMapping("/signup")
public class SignupController {
	
	@Autowired
	private UserAccountService userAccountService;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public UserMstDto saveUserData(@RequestBody UserMstDto dto) {
		return userAccountService.saveUserAccountData(dto);
	}
}
