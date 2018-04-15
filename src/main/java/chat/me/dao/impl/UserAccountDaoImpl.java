package chat.me.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import chat.me.dao.spec.UserAccountDao;
import chat.me.dto.UserAccountDto;

@Component
public class UserAccountDaoImpl implements UserAccountDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String SQL_INSERT = "insert into account_mst values(?,?,?,?,?,?)";
	
	private final String SQL_SELECT = "select * from account_mst where username = ?";
	
	private final String SQL_SELECT_ALL = "select * from account_mst";
	
	public void insert(UserAccountDto entity) {
		jdbcTemplate.update(SQL_INSERT, null, entity.getName(), entity.getEmail(), entity.getPhone(),
				entity.getUsername(), entity.getPassword());
	}

	public List<UserAccountDto> getAllUserAccounts() {
		return jdbcTemplate.query(SQL_SELECT_ALL, new BeanPropertyRowMapper(UserAccountDto.class));
	}
	
	@Override
	public UserAccountDto getByUsername(String username) {
		UserAccountDto result = (UserAccountDto) jdbcTemplate.queryForObject(SQL_SELECT, 
				new Object[] {username}, new BeanPropertyRowMapper(UserAccountDto.class));
		return result;
	}
	
}
