package chat.me.dao.spec;

import java.util.List;

import chat.me.entity.UserAccountEntity;

public interface UserAccountDao {

	void insert(UserAccountEntity entity);
	
	UserAccountEntity getByUsername(String username);
	
	List<UserAccountEntity> getAllUserAccounts();
}
