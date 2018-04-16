package chat.me.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

public class MessageTrnDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3231591947758033538L;

	private Long messageTrnId;
	private String message;
	private String username;
	private Timestamp lastUpdated;
	private String messageId;
	public Long getMessageTrnId() {
		return messageTrnId;
	}
	public void setMessageTrnId(Long messageTrnId) {
		this.messageTrnId = messageTrnId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Timestamp getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public MessageTrnDto(Long messageTrnId, String message, String username, Timestamp lastUpdated) {
		this.messageTrnId = messageTrnId;
		this.message = message;
		this.username = username;
		this.lastUpdated = lastUpdated;
	}
	public MessageTrnDto() {

	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	
}
