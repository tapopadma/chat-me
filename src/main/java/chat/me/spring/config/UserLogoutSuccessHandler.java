package chat.me.spring.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import chat.me.entity.ActiveUserEntity;

@Component("userLogoutSuccessHandler")
public class UserLogoutSuccessHandler implements LogoutSuccessHandler{
	
	@Autowired
	private ActiveUserEntity activeUserEntity; 
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		activeUserEntity.getUsers().remove(authentication.getName());
		
	}

}
