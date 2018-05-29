package chat.me.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import chat.me.dao.spec.ChannelDao;
import chat.me.dto.ChannelMstDto;
import chat.me.dto.ChannelUserMstDto;
import chat.me.dto.MessageTrnDto;
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
		List<ChannelUserMstDto> dtoList = jdbcTemplate.query(
				"select * from channel_user_mst where user_id=?",
				new Object[] { userId }, 
				new BeanPropertyRowMapper(ChannelUserMstDto.class));
		List<ChannelMstDto> dtos = getByPk(dtoList.stream().map(ChannelUserMstDto::getChannelId)
				.collect(Collectors.toList()));
		List<ChannelInfoEntity> resultList = new ArrayList<>();
		dtos.stream().forEach(dto->{
			ChannelInfoEntity entity = new ChannelInfoEntity();
			entity.setChannelMstDto(dto);
			resultList.add(entity);
		});
		return resultList;
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

	@Override
	public List<ChannelUserMstDto> getAllUserInfoByChannelId(String channelId) {
		return jdbcTemplate.query("select * from channel_user_mst where channel_id=?", 
				new Object[] {channelId},
				new BeanPropertyRowMapper(ChannelUserMstDto.class));
	}

	@Override
	public List<ChannelMstDto> getByPk(List<String> channelIds) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from channel_mst where ( ");
		for(int i=0;i<channelIds.size();++i) {
			sb.append(" channel_id = ? ");
			if(i < channelIds.size() - 1) {
				sb.append(" or ");
			}
		}
		sb.append(" )");
		Object[] args = new Object[channelIds.size()];
		for(int i=0;i<channelIds.size();++i)
			args[i] = channelIds.get(i);
		return jdbcTemplate.query(sb.toString(), 
				args,
				new BeanPropertyRowMapper(ChannelMstDto.class));
	}

}
