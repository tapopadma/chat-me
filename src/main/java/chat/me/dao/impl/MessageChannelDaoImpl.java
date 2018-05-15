package chat.me.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import chat.me.dao.spec.MessageChannelDao;
import chat.me.dto.MessageChannelTrnDto;
import chat.me.entity.ChannelmessageinfoEntity;

@Component
public class MessageChannelDaoImpl implements MessageChannelDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void insert(MessageChannelTrnDto dto) {

		String sql = "insert into message_channel_trn values(?, ?,  ?)";
		jdbcTemplate.update(sql, null, dto.getMessageId(), dto.getChannelId());
	}

	@Override
	public List<ChannelmessageinfoEntity> getAllMessageByChannel(String channelId) {
		String sql = "select * from message_channel_trn as m1 left join message_trn as m2 on "
				+ "m1.message_id = m2.message_id where m1.channel_id=? order by "
				+ "m2.last_updated";
		return jdbcTemplate.query(sql, 
				new Object[] {channelId}, new BeanPropertyRowMapper(
						ChannelmessageinfoEntity.class));
	}

}
