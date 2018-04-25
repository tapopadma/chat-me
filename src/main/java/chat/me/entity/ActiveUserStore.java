package chat.me.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ActiveUserStore {

	private List<String> userList;

	public List<String> getUserList() {
		return userList;
	}

	public void setUserList(List<String> userList) {
		this.userList = userList;
	}

	public ActiveUserStore(List<String> userList) {
		this.userList = userList;
	}

	public ActiveUserStore() {
		this.userList = new ArrayList<>();
	}
	
}
