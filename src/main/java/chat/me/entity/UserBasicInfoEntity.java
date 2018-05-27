package chat.me.entity;

import chat.me.dto.UserMstDto;

public class UserBasicInfoEntity {

	private UserMstDto userMstDto;
	
	private boolean loggedIn;

	public UserMstDto getUserMstDto() {
		return userMstDto;
	}

	public void setUserMstDto(UserMstDto userMstDto) {
		this.userMstDto = userMstDto;
	}


	public UserBasicInfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public UserBasicInfoEntity(UserMstDto userMstDto, boolean loggedIn) {
		super();
		this.userMstDto = userMstDto;
		this.loggedIn = loggedIn;
	}

	
}
