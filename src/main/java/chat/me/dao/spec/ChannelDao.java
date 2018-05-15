package chat.me.dao.spec;

import java.util.List;

import chat.me.dto.ChannelMstDto;
import chat.me.entity.ChannelmessageinfoEntity;

public interface ChannelDao {

	ChannelMstDto insert(ChannelMstDto dto);
	List<ChannelMstDto> insertInBatch(List<ChannelMstDto> dtoList);
	List<ChannelMstDto> getAllDtoByUsername(String username);
	String getChannelIdFromChannelName(String channelName);
}
