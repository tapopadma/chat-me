package chat.me.dto;

import java.io.Serializable;

public class UserMstDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1356801474228667422L;
	private String userId;
	private String userName;
	private String fullName;
	private String email;
	private String password;
	private String phone;
	public UserMstDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserMstDto(String userId, String userName, String fullName, 
			String email, String password, String phone) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
