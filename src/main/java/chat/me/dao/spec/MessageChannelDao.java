package chat.me.dao.spec;

import java.util.List;

import chat.me.dto.MessageChannelTrnDto;
import chat.me.entity.ChannelmessageinfoEntity;

public interface MessageChannelDao {

	void insert(MessageChannelTrnDto dto);
	List<ChannelmessageinfoEntity> getAllMessageByChannel(String channelId);
}
