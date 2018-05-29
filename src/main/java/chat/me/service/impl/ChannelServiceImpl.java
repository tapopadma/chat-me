package chat.me.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chat.me.dao.impl.ChannelDaoImpl;
import chat.me.dto.ChannelMstDto;
import chat.me.dto.ChannelUserMstDto;
import chat.me.entity.ChannelInfoEntity;
import chat.me.service.spec.ChannelService;

@Service
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	private ChannelDaoImpl channelDaoImpl;
	
	@Override
	public ChannelInfoEntity create(ChannelInfoEntity entity) {
		String newChannelId = UUID.randomUUID().toString();
		ChannelMstDto dto = entity.getChannelMstDto();
		dto.setChannelId(newChannelId);
		dto = channelDaoImpl.insert(dto);
		List<ChannelUserMstDto> dtoList = entity.getChannelUserMstDtoList();
		dtoList.stream().forEach(dto1->{
			dto1.setChannelId(newChannelId);
		});
		channelDaoImpl.insertInBatch(dtoList);
		return entity;
	}

	@Override
	public List<ChannelInfoEntity> getAll(String userId) {
		return channelDaoImpl.getAllChannelInfoByUserId(userId);
	}

}
