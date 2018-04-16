package chat.me.dto;

import java.io.Serializable;

public class MessageUsersTrnDto implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3457612156288400097L;

	
	private Long messageUsersTrnId;
	private String messageId;
	private String username;
	
	
	
	public Long getMessageUsersTrnId() {
		return messageUsersTrnId;
	}



	public void setMessageUsersTrnId(Long messageUsersTrnId) {
		this.messageUsersTrnId = messageUsersTrnId;
	}


	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}


	public MessageUsersTrnDto(Long messageUsersTrnId, String messageId, String username) {
		this.messageUsersTrnId = messageUsersTrnId;
		this.messageId = messageId;
		this.username = username;
	}



	public MessageUsersTrnDto() {

	}



	public String getMessageId() {
		return messageId;
	}



	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	
}
