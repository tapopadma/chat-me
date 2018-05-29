package chat.me.entity;

import java.sql.Timestamp;

import chat.me.dto.MessageTrnDto;

public class MessageWithDeliverystatusInfoEntity {

	private String messageId;
	private String message;
	private String messageMode;
	private Timestamp messageCreationTime;
	private String messageOperationStatus;
	private String sourceId;
	private String destinationId;
	
	private String messageDeliveryStatus;

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

	public String getMessageDeliveryStatus() {
		return messageDeliveryStatus;
	}

	public void setMessageDeliveryStatus(String messageDeliveryStatus) {
		this.messageDeliveryStatus = messageDeliveryStatus;
	}

	public MessageWithDeliverystatusInfoEntity(String messageId, String message, String messageMode,
			Timestamp messageCreationTime, String messageOperationStatus, String sourceId, String destinationId,
			String messageDeliveryStatus) {
		super();
		this.messageId = messageId;
		this.message = message;
		this.messageMode = messageMode;
		this.messageCreationTime = messageCreationTime;
		this.messageOperationStatus = messageOperationStatus;
		this.sourceId = sourceId;
		this.destinationId = destinationId;
		this.messageDeliveryStatus = messageDeliveryStatus;
	}

	public MessageWithDeliverystatusInfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static MessageTrnDto toMessageTrnDto(MessageWithDeliverystatusInfoEntity entity) {
		MessageTrnDto dto = new MessageTrnDto();
		dto.setDestinationId(entity.getDestinationId());
		dto.setMessage(entity.getMessage());
		dto.setMessageCreationTime(entity.getMessageCreationTime());
		dto.setMessageId(entity.getMessageId());
		dto.setMessageMode(entity.getMessageMode());
		dto.setMessageOperationStatus(entity.getMessageOperationStatus());
		dto.setSourceId(entity.getSourceId());
		return dto;
	}

}
