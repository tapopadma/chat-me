package chat.me.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ChannelMstDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7366956643471956051L;

	private String channelId;
	
	private String channelName;
	
	private Timestamp channelCreationDate;


	public ChannelMstDto(String channelId, String channelName, Timestamp channelCreationDate) {
		this.channelId = channelId;
		this.channelName = channelName;
		this.channelCreationDate = channelCreationDate;
	}

	public ChannelMstDto() {
		// TODO Auto-generated constructor stub
	}

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
}
