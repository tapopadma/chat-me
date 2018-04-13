package chat.me.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import chat.me.dao.spec.UserAccountDao;
import chat.me.entity.UserAccountEntity;

@Service
public class UserAccountServiceImpl implements UserDetailsService{
	@Autowired
	private UserAccountDao userAccountDao;

	public UserAccountEntity saveUserAccountData(UserAccountEntity entity) {
		userAccountDao.insert(entity);
		return entity;
	}

	public List<UserAccountEntity> getAllUsers(){
		return userAccountDao.getAllUserAccounts();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccountEntity entity = userAccountDao.getByUsername(username);
		if(entity == null) {
			throw new UsernameNotFoundException("username doesn't exist!!!");
		}
		List<GrantedAuthority> grantList = new ArrayList<>();
		grantList.add(new SimpleGrantedAuthority("USER"));
		UserDetails userDetails = new User(
				entity.getUsername(), entity.getPassword(), grantList);
		return userDetails;
	}
	
}
