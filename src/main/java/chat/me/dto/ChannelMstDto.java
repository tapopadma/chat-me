package chat.me.dto;

import java.io.Serializable;

public class ChannelMstDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7366956643471956051L;

	private String channelMstId;
	
	private String channelId;
	
	private String username;
	
	private String channelName;

	public ChannelMstDto(String channelMstId, String channelId, String username, String channelName) {
		super();
		this.channelMstId = channelMstId;
		this.channelId = channelId;
		this.username = username;
		this.channelName = channelName;
	}

	public String getChannelMstId() {
		return channelMstId;
	}

	public void setChannelMstId(String channelMstId) {
		this.channelMstId = channelMstId;
	}

	public ChannelMstDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
}
