package chat.me.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import chat.me.dao.spec.UserAccountDao;
import chat.me.dto.UserMstDto;

@Component
public class UserAccountDaoImpl implements UserAccountDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String SQL_INSERT = "insert into user_mst values(?,?,?,?,?,?)";
	
	private final String SQL_SELECT = "select * from user_mst where user_id = ?";
	
	private final String SQL_SELECT_ALL = "select * from user_mst";
	
	public void insert(UserMstDto dto) {
		jdbcTemplate.update(SQL_INSERT, dto.getUserId(), dto.getUserName(),
				dto.getFullName(), dto.getEmail(), dto.getPassword(),
				dto.getPhone());
	}

	public List<UserMstDto> getAllUserAccounts() {
		return jdbcTemplate.query(SQL_SELECT_ALL, new BeanPropertyRowMapper(UserMstDto.class));
	}
	
	@Override
	public UserMstDto getByUserId(String userId) {
		UserMstDto result = (UserMstDto) jdbcTemplate.queryForObject(SQL_SELECT, 
				new Object[] {userId}, new BeanPropertyRowMapper(UserMstDto.class));
		return result;
	}

	@Override
	public UserMstDto getByUserName(String userName) {
		return (UserMstDto) jdbcTemplate.queryForObject(
				"select * from user_mst where user_name = ?", 
				new Object[] {userName}, 
				new BeanPropertyRowMapper(UserMstDto.class));
	}
	
}
