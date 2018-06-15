package chat.me.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import chat.me.dto.UserIpTrnDto;
import chat.me.dto.UserMstDto;
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
	public UserMstDto getAuthentication() {
		String userName = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		return userAccountServiceImpl.getUserInfoByUserName(userName);
	}
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.GET, value="/getAllUsers")
	public List<UserBasicInfoEntity> getAllUsers(){
		List<UserBasicInfoEntity> res = new ArrayList<>();
		userAccountServiceImpl.getAllUsers().stream().forEach(
				dto -> {
					UserBasicInfoEntity entity = new UserBasicInfoEntity();
					entity.setUserMstDto(dto);
					entity.setLoggedIn(activeUserStore.getUserIds().contains(
							dto.getUserId()));
					res.add(entity);
				}
				);
		return res;
	}

	@ResponseBody
	@RequestMapping(method=RequestMethod.GET, value="/getUniqueVistors")
	public List<UserIpTrnDto> getUniqueVisitors(HttpServletRequest request){
		return userAccountServiceImpl.getAllUniqueIps(request.getRemoteAddr());
	}
}
