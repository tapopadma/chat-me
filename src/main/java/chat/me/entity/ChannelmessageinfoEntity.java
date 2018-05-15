package chat.me.entity;

import java.sql.Timestamp;

public class ChannelmessageinfoEntity {

	private String username;
	private String channelName;
	private String message;
	private String messageId;
	private Timestamp lastUpdated;
	
	public ChannelmessageinfoEntity(String username, String channelName, String message, String messageId,
			Timestamp lastUpdated, boolean isUsertyping) {
		super();
		this.setUsername(username);
		this.setChannelName(channelName);
		this.setMessage(message);
		this.setMessageId(messageId);
		this.setLastUpdated(lastUpdated);
	}

	public ChannelmessageinfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	
}
