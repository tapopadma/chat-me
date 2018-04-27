package chat.me.entity;

public class SocketMessageEntity {

	private MessageinfoEntity messageinfoEntity;
	
	private SessioninfoEntity sessioninfoEntity;

	public SocketMessageEntity(MessageinfoEntity messageinfoEntity, SessioninfoEntity sessioninfoEntity) {
		super();
		this.messageinfoEntity = messageinfoEntity;
		this.sessioninfoEntity = sessioninfoEntity;
	}

	public MessageinfoEntity getmessageinfoEntity() {
		return messageinfoEntity;
	}

	public void setmessageinfoEntity(MessageinfoEntity messageinfoEntity) {
		this.messageinfoEntity = messageinfoEntity;
	}

	public SessioninfoEntity getSessioninfoEntity() {
		return sessioninfoEntity;
	}

	public void setSessioninfoEntity(SessioninfoEntity sessioninfoEntity) {
		this.sessioninfoEntity = sessioninfoEntity;
	}

	public SocketMessageEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
