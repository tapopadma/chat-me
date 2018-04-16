package chat.me.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

	
}
