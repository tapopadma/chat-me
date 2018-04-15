package chat.me.service.spec;

import org.springframework.security.core.userdetails.UserDetails;

import chat.me.dto.UserAccountDto;

public interface UserAccountService {

	UserAccountDto saveUserAccountData(UserAccountDto entity);
	
	UserDetails getUserDetailsByUsername(String username);
}
