package chat.me.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserIpTrnDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1265718963250348714L;

	private String ip;
	private Timestamp lastVisited;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Timestamp getLastVisited() {
		return lastVisited;
	}
	public void setLastVisited(Timestamp lastVisited) {
		this.lastVisited = lastVisited;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public UserIpTrnDto(String ip, Timestamp lastVisited) {
		super();
		this.ip = ip;
		this.lastVisited = lastVisited;
	}
	public UserIpTrnDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
