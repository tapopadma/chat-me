package chat.me.dto;

import java.io.Serializable;

public class ChannelUserMstDto implements Serializable{

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ChannelUserMstDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChannelUserMstDto(String channelId, String userId, String userType) {
		super();
		this.channelId = channelId;
		this.userId = userId;
		this.userType = userType;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -640756468690083748L;

	private String channelId;
	
	private String userId;
	
	private String userType;
	
}
