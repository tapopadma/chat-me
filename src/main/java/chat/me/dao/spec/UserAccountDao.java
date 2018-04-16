package chat.me.dao.spec;

import java.util.List;

import chat.me.dto.AccountMstDto;

public interface UserAccountDao {

	void insert(AccountMstDto entity);
	
	AccountMstDto getByUsername(String username);
	
	List<AccountMstDto> getAllUserAccounts();
}
