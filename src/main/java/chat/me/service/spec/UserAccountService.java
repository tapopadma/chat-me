package chat.me.service.spec;

import org.springframework.security.core.userdetails.UserDetails;

import chat.me.dto.AccountMstDto;

public interface UserAccountService {

	AccountMstDto saveUserAccountData(AccountMstDto entity);
	
	UserDetails getUserDetailsByUsername(String username);
}
