package chat.me.entity;

public class SocketMessageEntity {
	
	//every time user reads message
	private MessageMarkAsReadInfoEntity messageMarkAsReadInfoEntity;

	//every time user types 
	private MessageTypingInfoEntity messageTypingInfoEntity;
	
	//every time user sends a message
	private MessageTrnInfoEntity messageTrnInfoEntity;
	
	//every time user logs in/out
	private UserSessionInfoEntity userSessionInfoEntity;


	public MessageTypingInfoEntity getMessageTypingInfoEntity() {
		return messageTypingInfoEntity;
	}

	public void setMessageTypingInfoEntity(MessageTypingInfoEntity messageTypingInfoEntity) {
		this.messageTypingInfoEntity = messageTypingInfoEntity;
	}

	public MessageTrnInfoEntity getMessageTrnInfoEntity() {
		return messageTrnInfoEntity;
	}

	public void setMessageTrnInfoEntity(MessageTrnInfoEntity messageTrnInfoEntity) {
		this.messageTrnInfoEntity = messageTrnInfoEntity;
	}

	public UserSessionInfoEntity getUserSessionInfoEntity() {
		return userSessionInfoEntity;
	}

	public void setUserSessionInfoEntity(UserSessionInfoEntity userSessionInfoEntity) {
		this.userSessionInfoEntity = userSessionInfoEntity;
	}

	public SocketMessageEntity(MessageMarkAsReadInfoEntity messageMarkAsReadInfoEntity,
			MessageTypingInfoEntity messageTypingInfoEntity, MessageTrnInfoEntity messageTrnInfoEntity,
			UserSessionInfoEntity userSessionInfoEntity) {
		super();
		this.messageMarkAsReadInfoEntity = messageMarkAsReadInfoEntity;
		this.messageTypingInfoEntity = messageTypingInfoEntity;
		this.messageTrnInfoEntity = messageTrnInfoEntity;
		this.userSessionInfoEntity = userSessionInfoEntity;
	}

	public SocketMessageEntity() {
		// TODO Auto-generated constructor stub
	}

	public MessageMarkAsReadInfoEntity getMessageMarkAsReadInfoEntity() {
		return messageMarkAsReadInfoEntity;
	}

	public void setMessageMarkAsReadInfoEntity(MessageMarkAsReadInfoEntity messageMarkAsReadInfoEntity) {
		this.messageMarkAsReadInfoEntity = messageMarkAsReadInfoEntity;
	}


}
