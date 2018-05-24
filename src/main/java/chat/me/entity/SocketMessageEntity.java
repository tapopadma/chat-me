package chat.me.entity;

import java.util.List;

public class SocketMessageEntity {

	private boolean isUsertyping;
	
	private ChannelmessageinfoEntity channelmessageinfoEntity; 
	
	private List<MessageinfoEntity> messageinfoentityList;
	
	private SessioninfoEntity sessioninfoEntity;


	public SocketMessageEntity(boolean isUsertyping, ChannelmessageinfoEntity channelmessageinfoEntity,
			List<MessageinfoEntity> messageinfoEntityList, SessioninfoEntity sessioninfoEntity) {
		this.isUsertyping = isUsertyping;
		this.channelmessageinfoEntity = channelmessageinfoEntity;
		this.setMessageinfoentityList(messageinfoEntityList);
		this.sessioninfoEntity = sessioninfoEntity;
	}

	public SessioninfoEntity getSessioninfoEntity() {
		return sessioninfoEntity;
	}

	public void setSessioninfoEntity(SessioninfoEntity sessioninfoEntity) {
		this.sessioninfoEntity = sessioninfoEntity;
	}

	public SocketMessageEntity() {

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


	public List<MessageinfoEntity> getMessageinfoentityList() {
		return messageinfoentityList;
	}


	public void setMessageinfoentityList(List<MessageinfoEntity> messageinfoEntityList) {
		this.messageinfoentityList = messageinfoEntityList;
	}
	
}
