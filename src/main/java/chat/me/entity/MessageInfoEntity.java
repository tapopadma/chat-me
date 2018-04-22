package chat.me.entity;

import java.sql.Timestamp;

public class MessageInfoEntity {
	
	private String fromUsername;
	private String toUsername;
	private String message;
	private String messageId;
	private Timestamp lastUpdated;
	private boolean isUsertyping;//of course refers to other user
	private boolean isUserloggedin;//of course refers to other user
	
	public String getFromUsername() {
		return fromUsername;
	}
	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}
	public String getToUsername() {
		return toUsername;
	}
	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
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
	public MessageInfoEntity() {
		// TODO Auto-generated constructor stub
	}
	public boolean getIsUsertyping() {
		return isUsertyping;
	}
	public void setIsUsertyping(boolean isUsertyping) {
		this.isUsertyping = isUsertyping;
	}
	public boolean isUserloggedin() {
		return isUserloggedin;
	}
	public void setUserloggedin(boolean isUserloggedin) {
		this.isUserloggedin = isUserloggedin;
	}
	public MessageInfoEntity(String fromUsername, String toUsername, String message, String messageId,
			Timestamp lastUpdated, boolean isUsertyping, boolean isUserloggedin) {
		super();
		this.fromUsername = fromUsername;
		this.toUsername = toUsername;
		this.message = message;
		this.messageId = messageId;
		this.lastUpdated = lastUpdated;
		this.isUsertyping = isUsertyping;
		this.isUserloggedin = isUserloggedin;
	}
	
	
	
}
