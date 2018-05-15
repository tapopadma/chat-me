package chat.me.entity;

public class SocketMessageEntity {

	private boolean isUsertyping;
	
	private ChannelmessageinfoEntity channelmessageinfoEntity; 
	
	private MessageinfoEntity messageinfoEntity;
	
	private SessioninfoEntity sessioninfoEntity;


	public SocketMessageEntity(boolean isUsertyping, ChannelmessageinfoEntity channelmessageinfoEntity,
			MessageinfoEntity messageinfoEntity, SessioninfoEntity sessioninfoEntity) {
		super();
		this.isUsertyping = isUsertyping;
		this.channelmessageinfoEntity = channelmessageinfoEntity;
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

	public ChannelmessageinfoEntity getChannelmessageinfoEntity() {
		return channelmessageinfoEntity;
	}

	public void setChannelmessageinfoEntity(ChannelmessageinfoEntity channelmessageinfoEntity) {
		this.channelmessageinfoEntity = channelmessageinfoEntity;
	}

	public boolean getIsUsertyping() {
		return isUsertyping;
	}

	public void setIsUsertyping(boolean isUsertyping) {
		this.isUsertyping = isUsertyping;
	}
	
}
