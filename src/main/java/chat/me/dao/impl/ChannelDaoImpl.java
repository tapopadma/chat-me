package chat.me.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import chat.me.dao.spec.ChannelDao;
import chat.me.dto.ChannelMstDto;
import chat.me.dto.ChannelUserMstDto;
import chat.me.entity.ChannelInfoEntity;

@Component
public class ChannelDaoImpl implements ChannelDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public ChannelMstDto insert(ChannelMstDto dto) {
		jdbcTemplate.update("insert into channel_mst values(?,?,?)", dto.getChannelId(), 
				dto.getChannelName(), dto.getChannelCreationDate());
		return getByPk(dto.getChannelId());
	}

	@Override
	public List<ChannelUserMstDto> insertInBatch(List<ChannelUserMstDto> dtoList) {
		jdbcTemplate.batchUpdate("insert into channel_user_mst values(?,?,?)", 
				new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ChannelUserMstDto dto = dtoList.get(i);
				ps.setString(1, dto.getChannelId());
				ps.setString(2, dto.getUserId());
				ps.setString(3, dto.getUserType());
			}

			@Override
			public int getBatchSize() {
				return dtoList.size();
			}
		});
		return dtoList;
	}

	@Override
	public List<ChannelInfoEntity> getAllChannelInfoByUserId(String userId) {
		return jdbcTemplate.query(
				"select T1.channel_id, T1.user_id, T1.user_type, "
				+ "T2.channel_name, T2.channel_creation_date from \n" + 
				"channel_user_mst T1 left join channel_mst T2 \n" + 
				"on T1.channel_id = T2.channel_id\n" + 
				"where T1.user_id = ?",
				new Object[] { userId }, 
				new BeanPropertyRowMapper(ChannelInfoEntity.class));
		
	}

	@Override
	public String getChannelIdFromChannelName(String channelName) {
		String sql = "select * from channel_mst where channel_name = ?";
		ChannelMstDto result = (ChannelMstDto) jdbcTemplate.query(sql, 
				new Object[] {channelName}, 
				new BeanPropertyRowMapper(ChannelMstDto.class)).get(0);
		return result.getChannelId();
	}

	@Override
	public ChannelMstDto getByPk(String channelId) {
		return (ChannelMstDto) jdbcTemplate.query("select * from channel_mst where channel_id=?", 
				new Object[] {channelId},
				new BeanPropertyRowMapper(ChannelMstDto.class)).get(0);
	}

}
