package chat.me.service.spec;

import java.util.List;

import chat.me.entity.ChannelmessageinfoEntity;
import chat.me.entity.MessageinfoEntity;

public interface MessengerService {

	ChannelmessageinfoEntity saveMessage(ChannelmessageinfoEntity entity);
	MessageinfoEntity saveMessage(MessageinfoEntity entity);
	List<MessageinfoEntity> fetchAllMessage(String fromUsername, String toUsername);
	List<ChannelmessageinfoEntity> fetchAllChannelMessage(String channelName);
	MessageinfoEntity saveMessage(MessageinfoEntity entity, boolean isUserTyping);
	ChannelmessageinfoEntity saveMessage(ChannelmessageinfoEntity entity, boolean isUserTyping);
}
