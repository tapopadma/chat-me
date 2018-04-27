package chat.me.entity;

public class SessioninfoEntity {
	
	public SessioninfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SessioninfoEntity(String username, boolean isLoginRequest, boolean isLogoutRequest) {
		super();
		this.username = username;
		this.isloginRequest = isLoginRequest;
		this.islogoutRequest = isLogoutRequest;
	}

	private String username;
	
	private boolean isloginRequest;
	
	private boolean islogoutRequest;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isLoginRequest() {
		return isloginRequest;
	}

	public void setLoginRequest(boolean isLoginRequest) {
		this.isloginRequest = isLoginRequest;
	}

	public boolean isLogoutRequest() {
		return islogoutRequest;
	}

	public void setLogoutRequest(boolean isLogoutRequest) {
		this.islogoutRequest = isLogoutRequest;
	}
}
