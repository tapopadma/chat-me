package chat.me.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chat.me.dao.spec.UserAccountCassandraRepository;
import chat.me.dao.spec.UserAccountDao;
import chat.me.dto.UserMstCassandraDto;
import chat.me.dto.UserMstDto;

@Component
public class UserAccountDaoCassandraImpl implements UserAccountDao{

	@Autowired
	private UserAccountCassandraRepository userAccountCassandraRepository;
	
	@Override
	public void insert(UserMstDto entity) {
		UserMstCassandraDto dto = entity.toCassandraDto();
		userAccountCassandraRepository.insert(dto);
	}

	@Override
	public List<UserMstDto> getAllUserAccounts() {
		return userAccountCassandraRepository.findAll()
				.stream().map(UserMstCassandraDto::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public UserMstDto getByUserName(String username) {
		return this.getAllUserAccounts().stream()
				.filter(dto->dto.getUserName().equals(username))
				.findFirst().get();
	}

}
