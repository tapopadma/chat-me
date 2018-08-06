package chat.me.entity;

import java.util.List;

public class MessageMarkAsReadInfoEntity {

	private List<String> messageIds;
	
	private String userId;
	
	private String messageMode;

	public MessageMarkAsReadInfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageMarkAsReadInfoEntity(List<String> messageIds, String userId, String messageMode) {
		super();
		this.messageIds = messageIds;
		this.userId = userId;
		this.setMessageMode(messageMode);
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

	public String getMessageMode() {
		return messageMode;
	}

	public void setMessageMode(String messageMode) {
		this.messageMode = messageMode;
	}
}
