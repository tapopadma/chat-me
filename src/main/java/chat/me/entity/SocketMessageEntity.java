package chat.me.entity;

public class SocketMessageEntity {

	//every time user types 
	private MessageMiscellaneousInfoEntity messageMiscellaneousInfoEntity;
	
	//every time user sends a message
	private MessageTrnInfoEntity messageTrnInfoEntity; 
	
	//every time user logs in/out
	private UserSessionInfoEntity userSessionInfoEntity;

	public MessageMiscellaneousInfoEntity getMessageMiscellaneousInfoEntity() {
		return messageMiscellaneousInfoEntity;
	}

	public void setMessageMiscellaneousInfoEntity(
			MessageMiscellaneousInfoEntity messageMiscellaneousInfoEntity) {
		this.messageMiscellaneousInfoEntity = messageMiscellaneousInfoEntity;
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

	public SocketMessageEntity(MessageMiscellaneousInfoEntity messageMiscellaneousInfoEntity,
			MessageTrnInfoEntity messageTrnInfoEntity, UserSessionInfoEntity userSessionInfoEntity) {
		this.messageMiscellaneousInfoEntity = messageMiscellaneousInfoEntity;
		this.messageTrnInfoEntity = messageTrnInfoEntity;
		this.userSessionInfoEntity = userSessionInfoEntity;
	}

	public SocketMessageEntity() {
		// TODO Auto-generated constructor stub
	}


}
