package chat.me.entity;

public class MessageOperationEntity {
	
	private String messageId;
	
	private String operation;
	
	private String messageMode;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public MessageOperationEntity(String messageId, String operation, String messageMode) {
		this.messageId = messageId;
		this.operation = operation;
		this.setMessageMode(messageMode);
	}

	public MessageOperationEntity() {
		// TODO Auto-generated constructor stub
	}

	public String getMessageMode() {
		return messageMode;
	}

	public void setMessageMode(String messageMode) {
		this.messageMode = messageMode;
	}
	
	

}
