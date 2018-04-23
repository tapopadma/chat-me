package chat.me.controller;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import chat.me.entity.ActiveUserEntity;

@Component
@Configurable
public class UserSessionManager  implements HttpSessionBindingListener{

	private String username;
	
	@Autowired
	private ActiveUserEntity activeUserEntity;
	
	public UserSessionManager(String username) {
		this.setUsername(username);
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		UserSessionManager user = (UserSessionManager) event.getValue();
        if (!activeUserEntity.getUsers().contains(user.getUsername())) {
        	activeUserEntity.getUsers().add(user.getUsername());
        }
		
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		UserSessionManager user = (UserSessionManager) event.getValue();
        if (activeUserEntity.getUsers().contains(user.getUsername())) {
        	activeUserEntity.getUsers().remove(user.getUsername());
        }
	}

	public UserSessionManager() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
