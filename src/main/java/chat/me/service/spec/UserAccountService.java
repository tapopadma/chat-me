package chat.me.service.spec;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import chat.me.dto.UserIpTrnDto;
import chat.me.dto.UserMstDto;

public interface UserAccountService {

	UserMstDto saveUserAccountData(UserMstDto dto);
	
	UserDetails getUserDetailsByUsername(String username);
	
	List<UserIpTrnDto> getAllUniqueIps(String ip);
}
