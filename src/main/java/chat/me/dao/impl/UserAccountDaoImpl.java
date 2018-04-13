package chat.me.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import chat.me.dao.spec.UserAccountDao;
import chat.me.entity.UserAccountEntity;

@Component
public class UserAccountDaoImpl implements UserAccountDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String SQL_INSERT = "insert into account_mst values(?,?,?,?,?,?)";
	
	private final String SQL_SELECT = "select * from account_mst where username = ?";
	
	private final String SQL_SELECT_ALL = "select * from account_mst";
	
	public void insert(UserAccountEntity entity) {
		jdbcTemplate.update(SQL_INSERT, null, entity.getName(), entity.getEmail(), entity.getPhone(),
				entity.getUsername(), entity.getPassword());
	}

	public List<UserAccountEntity> getAllUserAccounts() {
		return jdbcTemplate.queryForList(SQL_SELECT_ALL, UserAccountEntity.class);
	}
	
	@Override
	public UserAccountEntity getByUsername(String username) {
		UserAccountEntity result = jdbcTemplate.query(SQL_SELECT,
				ps -> ps.setString(1, username),
				(resultSet, rowNum) -> new UserAccountEntity(
						resultSet.getString("email"), 
						resultSet.getString("name"), resultSet.getString("password"), 
						resultSet.getString("phone"), resultSet.getString("username"))).get(0);
		return result;
	}
	
}
