package chat.me.dao.spec;

import java.util.List;

import chat.me.dto.ChannelMstDto;
import chat.me.dto.ChannelUserMstDto;
import chat.me.entity.ChannelInfoEntity;

public interface ChannelDao {

	ChannelMstDto getByPk(String channelId);
	ChannelMstDto insert(ChannelMstDto dto);
	List<ChannelUserMstDto> insertInBatch(List<ChannelUserMstDto> dtoList);
	List<ChannelInfoEntity> getAllChannelInfoByUserId(String username);
	String getChannelIdFromChannelName(String channelName);
	List<ChannelUserMstDto> getAllUserInfoByChannelId(String channelId);
	List<ChannelMstDto> getByPk(List<String> channelIds);
}
