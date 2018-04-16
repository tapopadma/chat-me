package chat.me.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import chat.me.dao.spec.UserAccountDao;
import chat.me.dto.AccountMstDto;

@Component
public class UserAccountDaoImpl implements UserAccountDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String SQL_INSERT = "insert into account_mst values(?,?,?,?,?,?)";
	
	private final String SQL_SELECT = "select * from account_mst where username = ?";
	
	private final String SQL_SELECT_ALL = "select * from account_mst";
	
	public void insert(AccountMstDto dto) {
		jdbcTemplate.update(SQL_INSERT, dto.getAccountMstId(), dto.getName(), dto.getEmail(), dto.getPhone(),
				dto.getUsername(), dto.getPassword());
	}

	public List<AccountMstDto> getAllUserAccounts() {
		return jdbcTemplate.query(SQL_SELECT_ALL, new BeanPropertyRowMapper(AccountMstDto.class));
	}
	
	@Override
	public AccountMstDto getByUsername(String username) {
		AccountMstDto result = (AccountMstDto) jdbcTemplate.queryForObject(SQL_SELECT, 
				new Object[] {username}, new BeanPropertyRowMapper(AccountMstDto.class));
		return result;
	}
	
}
