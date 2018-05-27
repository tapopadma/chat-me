package chat.me.entity;

import java.sql.Timestamp;

public class ChannelInfoEntity {

	private String channelId;
	private String channelName;
	private Timestamp channelCreationDate;
	private String userId;
	private String userType;
	
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Timestamp getChannelCreationDate() {
		return channelCreationDate;
	}
	public void setChannelCreationDate(Timestamp channelCreationDate) {
		this.channelCreationDate = channelCreationDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public ChannelInfoEntity(String channelId, String channelName, Timestamp channelCreationDate, String userId,
			String userType) {
		this.channelId = channelId;
		this.channelName = channelName;
		this.channelCreationDate = channelCreationDate;
		this.userId = userId;
		this.userType = userType;
	}
	public ChannelInfoEntity() {
		// TODO Auto-generated constructor stub
	}
	
}
