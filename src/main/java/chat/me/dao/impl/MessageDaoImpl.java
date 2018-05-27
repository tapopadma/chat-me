package chat.me.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import chat.me.dao.spec.MessageDao;
import chat.me.dto.MessageTrnDto;

@Component
public class MessageDaoImpl implements MessageDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String INSERT_SQL = "insert into message_trn values(?,?,?,?,?,?,?,?)";
	
	@Override
	public MessageTrnDto saveNewMessage(MessageTrnDto dto) {
		dto.setMessageId(UUID.randomUUID().toString());
		jdbcTemplate.update(INSERT_SQL, dto.getMessageId(), dto.getMessage(), 
				dto.getMessageMode(), dto.getMessageDeliveryStatus(), 
				dto.getMessageCreationTime(), dto.getMessageOperationStatus(),
				dto.getSourceId(), dto.getDestinationId());
		return dto;
	}

	@Override
	public List<MessageTrnDto> getBySourceAndDest(MessageTrnDto dto) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from message_trn where (source_id = ? and destination_id = ?)"
				+ " or (source_id = ? and destination_id = ?) order by message_creation_time");
		
		return jdbcTemplate.query(sb.toString(), 
				new Object [] {dto.getSourceId(), dto.getDestinationId(),
						dto.getDestinationId(), dto.getSourceId()},
				new BeanPropertyRowMapper(MessageTrnDto.class));
	}

	@Override
	public List<MessageTrnDto> updateMessageDeliveryStatus(
			List<String> messageIds, String deliveryStatus) {
		if(messageIds.isEmpty())
			return new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		sb.append("update message_trn set message_delivery_status = ? where ( ");
		for(int i=0;i<messageIds.size();++i) {
			sb.append(" message_id = ? ");
			if(i < messageIds.size() - 1) {
				sb.append(" or ");
			}
		}
		sb.append(" ) ");
		Object[] args = new Object[messageIds.size()+1];
		args[0] = deliveryStatus;
		for(int i=1;i<=messageIds.size();++i)
			args[i] = messageIds.get(i - 1);
		jdbcTemplate.update(sb.toString(), args);
		return getDtosByMessageIds(messageIds);
	}
	
	private List<MessageTrnDto> getDtosByMessageIds(List<String> messageIds) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from message_trn where ");
		for(int i=0;i<messageIds.size();++i) {
			sb.append(" message_id = ? ");
			if(i < messageIds.size() - 1) {
				sb.append(" or ");
			}
		}
		sb.append(" order by message_creation_time");
		Object[] args = new Object[messageIds.size()];
		for(int i=0;i<messageIds.size();++i)
			args[i] = messageIds.get(i);
		return jdbcTemplate.query(sb.toString(), 
				args, 
				new BeanPropertyRowMapper(MessageTrnDto.class));
	}

	@Override
	public List<MessageTrnDto> getByDestinationIdAndDeliveryStatus(
			String destinationId, String messageDeliveryStatus) {
		return jdbcTemplate.query("select * from message_trn where destination_id=? "
				+ "and message_delivery_status=? order by message_creation_time", 
				new Object[] {destinationId, messageDeliveryStatus},
				new BeanPropertyRowMapper(MessageTrnDto.class));
	}

	@Override
	public List<MessageTrnDto> getAllNotReadBySourceAndDest(String sourceId, String destinationId) {
		return jdbcTemplate.query("string * from message_trn where"
				+ " source_id=? and destination_id=?"
				+ " and message_delivery_status!='READ'", 
				new Object[] {sourceId, destinationId},
				new BeanPropertyRowMapper(MessageTrnDto.class));
	}
	
}
