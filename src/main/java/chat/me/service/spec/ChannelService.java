package chat.me.service.spec;

import java.util.List;

import chat.me.dto.ChannelMstDto;
import chat.me.entity.ChannelInfoEntity;

public interface ChannelService {

	public List<ChannelMstDto> create(ChannelInfoEntity entity);
	List<ChannelMstDto> getAll(String username);
}
