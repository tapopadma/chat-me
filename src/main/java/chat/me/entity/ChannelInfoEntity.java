package chat.me.entity;

import java.util.List;

public class ChannelInfoEntity {

	private String channelName;
	private List<String> userList;
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public List<String> getUserList() {
		return userList;
	}
	public void setUserList(List<String> userList) {
		this.userList = userList;
	}
	public ChannelInfoEntity(String channelName, List<String> userList) {
		super();
		this.channelName = channelName;
		this.userList = userList;
	}
	public ChannelInfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
}
