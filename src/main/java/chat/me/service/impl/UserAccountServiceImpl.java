package chat.me.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import chat.me.dao.spec.UserAccountDao;
import chat.me.dao.spec.UserIpDao;
import chat.me.dto.UserIpTrnDto;
import chat.me.dto.UserMstDto;

@Service
public class UserAccountServiceImpl implements UserDetailsService{
	@Autowired
	@Qualifier("userAccountDaoMySQLImpl")
	private UserAccountDao userAccountDao;
	@Autowired
	private UserIpDao userIpDao;

	public UserMstDto saveUserAccountData(UserMstDto dto) {
		dto.setUserId(UUID.randomUUID().toString());
		userAccountDao.insert(dto);
		return dto;
	}

	public List<UserMstDto> getAllUsers(){
		return userAccountDao.getAllUserAccounts();
	}
	
	public UserMstDto getUserInfoByUserName(String userName) {
		return userAccountDao.getByUserName(userName);
	}
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserMstDto dto = userAccountDao.getByUserName(userName);
		if(dto == null) {
			throw new UsernameNotFoundException("username doesn't exist!!!");
		}
		List<GrantedAuthority> grantList = new ArrayList<>();
		grantList.add(new SimpleGrantedAuthority("USER"));
		UserDetails userDetails = new User(
				dto.getUserName(), dto.getPassword(), grantList);
		return userDetails;
	}
	
	public List<UserIpTrnDto> getAllUniqueIps(String newIp){
		List<UserIpTrnDto> allDtos = userIpDao.getAllDtos();
		if(allDtos.stream().map(UserIpTrnDto::getIp).collect(Collectors.toList()).contains(newIp))
			return allDtos;
		userIpDao.insert(newIp);
		return userIpDao.getAllDtos();
	}
	
}
