package chat.me.entity;

import java.util.ArrayList;
import java.util.List;

public class ActiveUserEntity {

	private List<String> users;

	public ActiveUserEntity() {
		this.users = new ArrayList<>();
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}
	
	
}
