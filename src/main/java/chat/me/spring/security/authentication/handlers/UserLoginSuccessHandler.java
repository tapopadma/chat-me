package chat.me.spring.security.authentication.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import chat.me.dao.spec.UserAccountDao;
import chat.me.entity.ActiveUserStore;

@Component("userLoginSuccessHandler")
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	private RedirectStrategy redirectStrategy;
	
	@Autowired
	private ActiveUserStore activeUserStore;
	
	@Autowired
	@Qualifier("userAccountDaoCassandraImpl")
	private UserAccountDao userAccountDao;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String userName = authentication.getName();
		String userId = userAccountDao.getByUserName(userName).getUserId();
        if(!activeUserStore.getUserIds().contains(userId))
        	activeUserStore.getUserIds().add(userId);
        redirectStrategy.sendRedirect(request, response, "/app/messenger/");
	}
	
}
