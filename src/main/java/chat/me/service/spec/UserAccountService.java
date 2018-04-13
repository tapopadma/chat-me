package chat.me.service.spec;

import org.springframework.security.core.userdetails.UserDetails;

import chat.me.entity.UserAccountEntity;

public interface UserAccountService {

	UserAccountEntity saveUserAccountData(UserAccountEntity entity);
	
	UserDetails getUserDetailsByUsername(String username);
}
