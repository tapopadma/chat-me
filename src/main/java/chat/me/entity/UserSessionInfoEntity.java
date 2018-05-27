package chat.me.entity;

public class UserSessionInfoEntity {
	
	public UserSessionInfoEntity() {
		// TODO Auto-generated constructor stub
	}


	public UserSessionInfoEntity(String userId, boolean loginRequest, boolean logoutRequest) {
		super();
		this.setUserId(userId);
		this.loginRequest = loginRequest;
		this.logoutRequest = logoutRequest;
	}


	private String userId;
	
	private boolean loginRequest;
	
	private boolean logoutRequest;

	public boolean isLoginRequest() {
		return loginRequest;
	}


	public void setLoginRequest(boolean loginRequest) {
		this.loginRequest = loginRequest;
	}


	public boolean isLogoutRequest() {
		return logoutRequest;
	}


	public void setLogoutRequest(boolean logoutRequest) {
		this.logoutRequest = logoutRequest;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


}
