package chat.me.service.spec;

import org.springframework.security.core.userdetails.UserDetails;

import chat.me.dto.UserMstDto;

public interface UserAccountService {

	UserMstDto saveUserAccountData(UserMstDto dto);
	
	UserDetails getUserDetailsByUsername(String username);
}
