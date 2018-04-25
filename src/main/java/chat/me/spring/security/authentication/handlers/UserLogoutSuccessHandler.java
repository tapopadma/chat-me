package chat.me.spring.security.authentication.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import chat.me.entity.ActiveUserStore;

@Component("userLogoutSuccessHandler")
public class UserLogoutSuccessHandler implements LogoutSuccessHandler{
	
	@Autowired
	private ActiveUserStore activeUserStore;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("user");
            if(activeUserStore.getUserList().contains(authentication.getName()))
            	activeUserStore.getUserList().remove(authentication.getName());
        }
	}

}
