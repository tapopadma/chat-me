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

@Component
public class ChannelDaoImpl implements ChannelDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final String SQL_INSERT = "insert into channel_mst values(?,?,?,?)";

	@Override
	public ChannelMstDto insert(ChannelMstDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChannelMstDto> insertInBatch(List<ChannelMstDto> dtoList) {
		jdbcTemplate.batchUpdate(SQL_INSERT, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ChannelMstDto dto = dtoList.get(i);
				ps.setString(2, dto.getChannelId());
				ps.setString(3, dto.getUsername());
				ps.setString(3, dto.getChannelName());
			}

			@Override
			public int getBatchSize() {
				return dtoList.size();
			}
		});
		return dtoList;
	}

	@Override
	public List<ChannelMstDto> getAllDtoByUsername(String username) {
		String sql = "select * from channel_mst where username=?";
		return jdbcTemplate.query(sql, new Object[] { username }, 
				new BeanPropertyRowMapper(ChannelMstDto.class));
	}

}
