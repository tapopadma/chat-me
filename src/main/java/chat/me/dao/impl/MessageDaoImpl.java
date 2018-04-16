package chat.me.dao.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import chat.me.dao.spec.MessageDao;
import chat.me.dto.MessageTrnDto;

@Component
public class MessageDaoImpl implements MessageDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String INSERT_SQL = "insert into message_trn values(?,?,?,?,?)";
	
	@Override
	public String saveMessageByUsername(String message, String username) {
		if(message.isEmpty())
			return null;
		MessageTrnDto dto = new MessageTrnDto();
		dto.setMessage(message);
		dto.setUsername(username);
		dto.setMessageId(UUID.randomUUID().toString());
		jdbcTemplate.update(INSERT_SQL, dto.getMessageTrnId(), dto.getMessage(), 
				dto.getUsername(), dto.getLastUpdated(), dto.getMessageId());
		return dto.getMessageId();
	}

	
}
