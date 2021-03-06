package chat.me.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import chat.me.entity.MessageWithDeliverystatusInfoEntity;

public class MessageTrnDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3231591947758033538L;

	private String messageId;
	private String message;
	private String messageMode;
	private Timestamp messageCreationTime;
	private String messageOperationStatus;
	private String sourceId;
	private String destinationId;
	
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessageMode() {
		return messageMode;
	}
	public void setMessageMode(String messageMode) {
		this.messageMode = messageMode;
	}
	public Timestamp getMessageCreationTime() {
		return messageCreationTime;
	}
	public void setMessageCreationTime(Timestamp messageCreationTime) {
		this.messageCreationTime = messageCreationTime;
	}
	public String getMessageOperationStatus() {
		return messageOperationStatus;
	}
	public void setMessageOperationStatus(String messageOperationStatus) {
		this.messageOperationStatus = messageOperationStatus;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getDestinationId() {
		return destinationId;
	}
	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public MessageTrnDto(String messageId, String message, String messageMode,
			Timestamp messageCreationTime, String messageOperationStatus, 
			String sourceId, String destinationId) {
		this.messageId = messageId;
		this.message = message;
		this.messageMode = messageMode;
		this.messageCreationTime = messageCreationTime;
		this.messageOperationStatus = messageOperationStatus;
		this.sourceId = sourceId;
		this.destinationId = destinationId;
	}
	public MessageTrnDto() {
		// TODO Auto-generated constructor stub
	}
	
	public static MessageWithDeliverystatusInfoEntity toMessageWithDeliverystatusInfoEntity(
			MessageTrnDto dto, String messageDeliveryStatus) {
		
		MessageWithDeliverystatusInfoEntity entity = new MessageWithDeliverystatusInfoEntity();
		
		entity.setDestinationId(dto.getDestinationId());
		entity.setMessage(dto.getMessage());
		entity.setMessageCreationTime(dto.getMessageCreationTime());
		
		entity.setMessageDeliveryStatus(messageDeliveryStatus);
		
		entity.setMessageId(dto.getMessageId());
		entity.setMessageMode(dto.getMessageMode());
		entity.setMessageOperationStatus(dto.getMessageOperationStatus());
		entity.setSourceId(dto.getSourceId());
		
		return entity;
	}
	
}
