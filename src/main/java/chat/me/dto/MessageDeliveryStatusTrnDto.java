package chat.me.dto;

import java.io.Serializable;

public class MessageDeliveryStatusTrnDto implements Serializable{

	public MessageDeliveryStatusTrnDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MessageDeliveryStatusTrnDto(String messageId, String userId, String messageDeliveryStatus) {
		super();
		this.messageId = messageId;
		this.userId = userId;
		this.messageDeliveryStatus = messageDeliveryStatus;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMessageDeliveryStatus() {
		return messageDeliveryStatus;
	}
	public void setMessageDeliveryStatus(String messageDeliveryStatus) {
		this.messageDeliveryStatus = messageDeliveryStatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -1888569502041283864L;
	private String messageId;
	private String userId;
	private String messageDeliveryStatus;
}
