package chat.me.spring.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import chat.me.controller.UserSessionManager;
import chat.me.entity.ActiveUserEntity;

@Component("userLoginSuccessHandler")
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	private ActiveUserEntity activeUserEntity; 
	
	@Autowired
	private RedirectStrategy redirectStrategy;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		HttpSession session = request.getSession(false);
        if (session != null) {
            UserSessionManager user = new UserSessionManager(authentication.getName());
            session.setAttribute("user", user);
        }
        redirectStrategy.sendRedirect(request, response, "/app/main.html");
	}

	@Bean
	public RedirectStrategy redirectStrategy() {
		return new DefaultRedirectStrategy();
	}
	
}
