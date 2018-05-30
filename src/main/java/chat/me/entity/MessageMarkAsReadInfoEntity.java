package chat.me.entity;

import java.util.List;

public class MessageMarkAsReadInfoEntity {

	private List<String> messageIds;
	
	private String userId;

	public MessageMarkAsReadInfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageMarkAsReadInfoEntity(List<String> messageIds, String userId) {
		super();
		this.messageIds = messageIds;
		this.userId = userId;
	}

	public List<String> getMessageIds() {
		return messageIds;
	}

	public void setMessageIds(List<String> messageIds) {
		this.messageIds = messageIds;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
