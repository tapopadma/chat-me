package chat.me.entity;

public class MessageOperationEntity {
	
	private String messageId;
	
	private String operation;

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

	public MessageOperationEntity(String messageId, String operation) {
		super();
		this.messageId = messageId;
		this.operation = operation;
	}

	public MessageOperationEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
