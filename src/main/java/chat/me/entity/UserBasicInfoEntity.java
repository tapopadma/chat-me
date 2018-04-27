package chat.me.entity;

import chat.me.dto.AccountMstDto;

public class UserBasicInfoEntity {

	private AccountMstDto accountMstDto;
	
	private boolean isloggedIn;

	public AccountMstDto getAccountMstDto() {
		return accountMstDto;
	}

	public void setAccountMstDto(AccountMstDto accountMstDto) {
		this.accountMstDto = accountMstDto;
	}

	public boolean isLoggedIn() {
		return isloggedIn;
	}

	public void setLoggedIn(boolean isloggedIn) {
		this.isloggedIn = isloggedIn;
	}

	public UserBasicInfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserBasicInfoEntity(AccountMstDto accountMstDto, boolean isloggedIn) {
		super();
		this.accountMstDto = accountMstDto;
		this.isloggedIn = isloggedIn;
	}
	
}
