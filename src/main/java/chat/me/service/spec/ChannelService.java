package chat.me.service.spec;

import java.util.List;

import chat.me.entity.ChannelInfoEntity;

public interface ChannelService {

	List<ChannelInfoEntity> create(List<ChannelInfoEntity> entityList);
	List<ChannelInfoEntity> getAll(String username);
}
