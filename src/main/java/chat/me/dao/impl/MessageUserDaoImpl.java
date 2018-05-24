package chat.me.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import chat.me.dao.spec.MessageUserDao;
import chat.me.dto.MessageUsersTrnDto;

@Component
public class MessageUserDaoImpl implements MessageUserDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String INSERT_SQL = "insert into message_users_trn values(?,?,?)";
	
	@Override
	public void saveMessageByMessageIdAndUserName(String messageId, String username) {
		if(messageId == null)
			return;
		MessageUsersTrnDto dto = new MessageUsersTrnDto();
		dto.setMessageId(messageId);
		dto.setUsername(username);
		jdbcTemplate.update(INSERT_SQL, dto.getMessageUsersTrnId(), dto.getMessageId(), dto.getUsername());
	}

	@Override
	public List<MessageUsersTrnDto> getByUsername(String username) {
		return jdbcTemplate.query("select m1.* from \n" + 
				"message_users_trn m1 left join message_trn m2\n" + 
				"on m1.message_id = m2.message_id\n" + 
				"where m1.username = ? && m2.username != ?", 
				new Object[] {username, username}, 
				new BeanPropertyRowMapper(MessageUsersTrnDto.class));
	}
	
}
