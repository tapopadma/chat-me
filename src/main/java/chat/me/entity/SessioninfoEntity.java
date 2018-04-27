package chat.me.entity;

public class SessioninfoEntity {
	
	public SessioninfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public SessioninfoEntity(String username, boolean isloginRequest, boolean islogoutRequest) {
		super();
		this.username = username;
		this.isloginRequest = isloginRequest;
		this.islogoutRequest = islogoutRequest;
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

	public boolean getIsloginRequest() {
		return isloginRequest;
	}

	public void setIsloginRequest(boolean isloginRequest) {
		this.isloginRequest = isloginRequest;
	}

	public boolean getIslogoutRequest() {
		return islogoutRequest;
	}

	public void setIslogoutRequest(boolean islogoutRequest) {
		this.islogoutRequest = islogoutRequest;
	}

}
