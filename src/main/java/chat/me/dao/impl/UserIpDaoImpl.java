package chat.me.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import chat.me.dao.spec.UserIpDao;
import chat.me.dto.UserIpTrnDto;

@Component
public class UserIpDaoImpl implements UserIpDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<UserIpTrnDto> getAllDtos() {
		return jdbcTemplate.query("select * from user_ip_trn", 
				new BeanPropertyRowMapper(UserIpTrnDto.class));
	}

	public void insert(String ip) {
		jdbcTemplate.update("insert into user_ip_trn values(?,?)",ip, null);
	}
		
}
