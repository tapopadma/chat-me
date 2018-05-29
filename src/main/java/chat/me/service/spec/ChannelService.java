package chat.me.service.spec;

import java.util.List;

import chat.me.entity.ChannelInfoEntity;

public interface ChannelService {

	ChannelInfoEntity create(ChannelInfoEntity entity);
	List<ChannelInfoEntity> getAll(String username);
}
