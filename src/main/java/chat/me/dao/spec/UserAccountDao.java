package chat.me.dao.spec;

import java.util.List;

import chat.me.dto.UserMstDto;

public interface UserAccountDao {

	void insert(UserMstDto entity);
	
	UserMstDto getByUserId(String username);
	
	List<UserMstDto> getAllUserAccounts();

	UserMstDto getByUserName(String username);
}
