package chat.me.dto;

import java.io.Serializable;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value="user_mst")
public class UserMstCassandraDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PrimaryKeyColumn(name="user_id", ordinal = 0, type=PrimaryKeyType.PARTITIONED)
	private String userId;
	@Column(value="user_name")
	private String userName;
	@Column(value="full_name")
	private String fullName;
	@Column(value="email")
	private String email;
	@Column(value="password")
	private String password;
	@Column(value="phone")
	private String phone;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public UserMstCassandraDto(String userId, String userName, String fullName, String email, String password,
			String phone) {
		this.userId = userId;
		this.userName = userName;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}
	public UserMstCassandraDto() {
	}
	
	
	public UserMstDto toDto() {
		UserMstDto dto = new UserMstDto();
		dto.setEmail(this.getEmail());
		dto.setFullName(this.getFullName());
		dto.setPassword(this.getPassword());
		dto.setPhone(this.getPhone());
		dto.setUserId(this.getUserId());
		dto.setUserName(this.getUserName());
		return dto;
	}
	
}
