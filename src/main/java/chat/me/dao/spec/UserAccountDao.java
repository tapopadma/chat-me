package chat.me.dao.spec;

import java.util.List;

import chat.me.dto.UserAccountDto;

public interface UserAccountDao {

	void insert(UserAccountDto entity);
	
	UserAccountDto getByUsername(String username);
	
	List<UserAccountDto> getAllUserAccounts();
}
