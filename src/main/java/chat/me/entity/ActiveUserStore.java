package chat.me.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ActiveUserStore {

	private List<String> userIds;


	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public ActiveUserStore(List<String> userList) {
		this.userIds = userList;
	}

	public ActiveUserStore() {
		this.userIds = new ArrayList<>();
	}
	
}
