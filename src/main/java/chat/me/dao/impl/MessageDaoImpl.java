package chat.me.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import chat.me.dao.spec.MessageDao;
import chat.me.dto.MessageDeliveryStatusTrnDto;
import chat.me.dto.MessageTrnDto;
import chat.me.entity.MessageWithDeliverystatusInfoEntity;

@Component
public class MessageDaoImpl implements MessageDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String DIRECT_MODE = "DIRECT";
	private final String CHANNEL_MODE = "CHANNEL";
	
	@Override
	public MessageTrnDto saveNewMessage(MessageTrnDto dto, 
			List<MessageDeliveryStatusTrnDto> dtoList) {
		jdbcTemplate.update("insert into message_trn values(?,?,?,?,?,?,?)", 
				dto.getMessageId(), dto.getMessage(), 
				dto.getMessageMode(), 
				new Timestamp(new Date().getTime()), dto.getMessageOperationStatus(),
				dto.getSourceId(), dto.getDestinationId());
		jdbcTemplate.batchUpdate("insert into message_delivery_status_trn values(?,?,?)", 
				new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						MessageDeliveryStatusTrnDto statusDto = dtoList.get(i);
						ps.setString(1, statusDto.getMessageId());
						ps.setString(2, statusDto.getUserId());
						ps.setString(3, statusDto.getMessageDeliveryStatus());
					}
					
					@Override
					public int getBatchSize() {
						return dtoList.size();
					}
				});
		return dto;
	}

	@Override
	public List<MessageWithDeliverystatusInfoEntity> getBySourceAndDest(MessageTrnDto dto) {
		if(dto.getMessageMode().equals(DIRECT_MODE)) {
			StringBuilder sb = new StringBuilder();
			sb.append("select T1.*, T2.message_delivery_status from message_trn T1"
					+ " left join message_delivery_status_trn T2 "
					+ " on T1.message_id=T2.message_id "
					+ " where ((T1.source_id = ? and T1.destination_id = ?)"
					+ " or (T1.source_id = ? and T1.destination_id = ?))"
					+ " and T2.user_id=? and T1.message_operation_status != 'DELETE' "
					+ "order by T1.message_creation_time");
			
			return jdbcTemplate.query(sb.toString(), 
					new Object [] {dto.getSourceId(), dto.getDestinationId(),
							dto.getDestinationId(), dto.getSourceId(), dto.getDestinationId()},
					new BeanPropertyRowMapper(MessageWithDeliverystatusInfoEntity.class));
		}
		else {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT T1.*, IF( " + 
					" ( SELECT COUNT(*) FROM message_delivery_status_trn T2 " + 
					" WHERE T2.message_id=T1.message_id AND message_delivery_status != 'READ') > 0," + 
					"'UNREAD', 'READ') AS message_delivery_status FROM message_trn T1 " + 
					" WHERE T1.destination_id = ?  and T1.message_operation_status != 'DELETE' " + 
					" ORDER BY T1.message_creation_time");
			
			return jdbcTemplate.query(sb.toString(), 
					new Object [] {dto.getDestinationId()},
					new BeanPropertyRowMapper(MessageWithDeliverystatusInfoEntity.class));
		}
	}

	@Override
	public List<MessageTrnDto> updateMessageDeliveryStatus(List<String> messageIds, String userId, String deliveryStatus) {
		Object[] args = new Object[messageIds.size() + 2];
		StringBuilder sb = new StringBuilder();
		sb.append("update message_delivery_status_trn "
				+ "set message_delivery_status = ? where user_id = ?"
				+ " and message_delivery_status != 'READ'");
		sb.append(" and ( ");
		args[0] = deliveryStatus;
		args[1] = userId;
		for(int i=0;i<messageIds.size();++i) {
			args[i + 2] = messageIds.get(i);
			if(i + 1 < messageIds.size()) {
				sb.append(" message_id = ? or ");
			}
			else {
				sb.append(" message_id = ? )");
			}
		}
		jdbcTemplate.update(sb.toString(),args);
		return getDtosByMessageIds(messageIds);
	}
	
	@Override
	public List<MessageTrnDto> updateMessageDeliveryStatus(String userId, String deliveryStatus) {
		List<MessageDeliveryStatusTrnDto> dtos = jdbcTemplate.query(
				"select * from message_delivery_status_trn"
				+ " where user_id=? and message_delivery_status != 'READ'", 
				new Object[] {userId},
				new BeanPropertyRowMapper(MessageDeliveryStatusTrnDto.class));
		jdbcTemplate.update("update message_delivery_status_trn "
				+ "set message_delivery_status = ? where user_id = ?"
				+ " and message_delivery_status != 'READ'",
				deliveryStatus, userId);
		return getDtosByMessageIds(dtos.stream().map(MessageDeliveryStatusTrnDto::getMessageId)
				.collect(Collectors.toList()));
	}
	
	private List<MessageTrnDto> getDtosByMessageIds(List<String> messageIds) {
		if(messageIds.isEmpty())
			return new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		sb.append("select * from message_trn where ");
		for(int i=0;i<messageIds.size();++i) {
			sb.append(" message_id = ? ");
			if(i < messageIds.size() - 1) {
				sb.append(" or ");
			}
		}
		sb.append("  and message_operation_status != 'DELETE' order by message_creation_time");
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
				+ "and message_delivery_status=?  and message_operation_status != 'DELETE' order by message_creation_time", 
				new Object[] {destinationId, messageDeliveryStatus},
				new BeanPropertyRowMapper(MessageTrnDto.class));
	}

	@Override
	public List<MessageTrnDto> getAllNotReadBySourceAndDest(String sourceId, String destinationId) {
		return jdbcTemplate.query("string * from message_trn where"
				+ " source_id=? and destination_id=?"
				+ " and message_delivery_status!='READ' and message_operation_status != 'DELETE' ", 
				new Object[] {sourceId, destinationId},
				new BeanPropertyRowMapper(MessageTrnDto.class));
	}

	@Override
	public List<MessageTrnDto> getByDestinationIdAndDeliveryStatus(List<String> destinationIds, 
			String userId, String messageDeliveryStatus) {
		if(destinationIds.isEmpty())
			return new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		Object[] args = new Object[destinationIds.size()+2];
		sb.append("select T1.* from message_trn T1 left join message_user_trn T2"
				+ " on T1.message_id = T2.message_id where ( ");
		for(int i=0;i<destinationIds.size();++i) {
			sb.append(" T1.destination_id = ? ");
			if(i < destinationIds.size() - 1) {
				sb.append(" or ");
			}
			args[i] = destinationIds.get(i);
		}
		sb.append(" ) and T2.user_id = ? and T2.message_delivery_status = ? and T1.message_operation_status != 'DELETE' ");
		args[destinationIds.size()] = userId;
		args[destinationIds.size() + 1] = messageDeliveryStatus;
		return jdbcTemplate.query(sb.toString(), 
				args,
				new BeanPropertyRowMapper(MessageTrnDto.class));
	}

	@Override
	public List<MessageTrnDto> getAllNotReadByDestIds(List<String> destinationIds, String userId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select T1.* \n");
		sb.append("from message_trn T1 \n");
		sb.append("left join message_delivery_status_trn T2\n"); 
		sb.append("on T1.message_id = T2.message_id\n");
		sb.append("where ( ");
		Object [] args = new Object[destinationIds.size() + 1];
		for(int i=0;i<destinationIds.size();++i) {
			if(i + 1 < destinationIds.size()) {
				sb.append(" T1.destination_id=? or ");
			}
			else {
				sb.append(" T1.destination_id=? ");
			}
			args[i] = destinationIds.get(i);
		}
		args[destinationIds.size()] = userId;
		sb.append(" ) and T2.user_id =? and T2.message_delivery_status!='READ' and T1.message_operation_status != 'DELETE' ");
		return jdbcTemplate.query(sb.toString(),
				args, new BeanPropertyRowMapper(MessageTrnDto.class));
	}

	@Override
	public List<MessageTrnDto> getAllNotReadByUserId(String userId) {
		List<MessageDeliveryStatusTrnDto> dtos = jdbcTemplate.query("select * from "
				+ " message_delivery_status_trn where "
				+ " user_id = ? and message_delivery_status!='READ'", 
				new Object[] {userId},
				new BeanPropertyRowMapper(MessageDeliveryStatusTrnDto.class));
		return getDtosByMessageIds(dtos.stream().map(MessageDeliveryStatusTrnDto::getMessageId)
				.collect(Collectors.toList()));
	}

	@Override
	public void deleteMessageById(String messageId) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE message_trn SET message_operation_status = 'DELETE'\n" + 
				"WHERE message_id = ?");
		jdbcTemplate.update(sb.toString(), messageId);
	}
	
}
