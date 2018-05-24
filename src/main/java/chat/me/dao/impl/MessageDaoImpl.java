package chat.me.dao.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import chat.me.dao.spec.MessageDao;
import chat.me.dto.MessageTrnDto;
import chat.me.entity.MessageinfoEntity;

@Component
public class MessageDaoImpl implements MessageDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String INSERT_SQL = "insert into message_trn values(?,?,?,?,?,?)";
	
	@Override
	public MessageTrnDto saveMessageByUsername(String message, 
			String username, String deliveryStatus) {
		if(message.isEmpty())
			return null;
		MessageTrnDto dto = new MessageTrnDto();
		dto.setMessage(message);
		dto.setUsername(username);
		dto.setMessageId(UUID.randomUUID().toString());
		dto.setDeliveryStatus(deliveryStatus);
		jdbcTemplate.update(INSERT_SQL, dto.getMessageTrnId(), dto.getMessage(), 
				dto.getUsername(), dto.getLastUpdated(), dto.getMessageId(), dto.getDeliveryStatus());
		return dto;
	}

	@Override
	public List<MessageinfoEntity> fetchAllMessage(String fromUsername, String toUsername) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT m1.message_id, m1.message, m1.username as fromUsername, "
				+ "m2.username as toUsername, m1.last_updated, m1.delivery_status as deliveryStatus\n" + 
				"FROM message_trn m1\n" + 
				"LEFT JOIN message_users_trn m2 ON m1.message_id = m2.message_id\n" + 
				"WHERE m1.username IN (?, ?)\n" + 
				"and m2.username IN (?, ?)\n" + 
				"and m1.username != m2.username\n" + 
				"order by m1.last_updated;");
		
		return jdbcTemplate.query(sb.toString(), new Object [] {fromUsername, toUsername, 
				fromUsername, toUsername},
				new BeanPropertyRowMapper(MessageinfoEntity.class));
	}

	@Override
	public List<MessageTrnDto> updateMessageDeliveryStatus(
			List<String> messageIds, String deliveryStatus) {
		StringBuilder sb = new StringBuilder();
		sb.append("update message_trn set delivery_status = ? where ( ");
		for(int i=0;i<messageIds.size();++i) {
			sb.append(" message_id = ? ");
			if(i < messageIds.size() - 1) {
				sb.append(" or ");
			}
		}
		sb.append(" ) and delivery_status != 'READ' ");
		Object[] args = new Object[messageIds.size()+1];
		args[0] = deliveryStatus;
		for(int i=1;i<=messageIds.size();++i)
			args[i] = messageIds.get(i - 1);
		jdbcTemplate.update(sb.toString(), args);
		return getDtoByMessageId(messageIds);
	}
	
	private List<MessageTrnDto> getDtoByMessageId(List<String> messageIds) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from message_trn where ");
		for(int i=0;i<messageIds.size();++i) {
			sb.append(" message_id = ? ");
			if(i < messageIds.size() - 1) {
				sb.append(" or ");
			}
		}
		Object[] args = new Object[messageIds.size()];
		for(int i=0;i<messageIds.size();++i)
			args[i] = messageIds.get(i);
		return jdbcTemplate.query(sb.toString(), 
				args, 
				new BeanPropertyRowMapper(MessageTrnDto.class));
	}
	
}
