package chat.me.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chat.me.dao.spec.ChannelDao;
import chat.me.dto.ChannelMstDto;
import chat.me.dto.ChannelUserMstDto;
import chat.me.entity.ChannelInfoEntity;
import chat.me.service.spec.ChannelService;

@Service
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	private ChannelDao channelDao;
	
	@Override
	public ChannelInfoEntity create(ChannelInfoEntity entity) {
		String newChannelId = UUID.randomUUID().toString();
		ChannelMstDto dto = entity.getChannelMstDto();
		dto.setChannelId(newChannelId);
		dto = channelDao.insert(dto);
		List<ChannelUserMstDto> dtoList = entity.getChannelUserMstDtoList();
		dtoList.stream().forEach(dto1->{
			dto1.setChannelId(newChannelId);
		});
		channelDao.insertInBatch(dtoList);
		return entity;
	}

	@Override
	public List<ChannelInfoEntity> getAll(String userId) {
		return channelDao.getAllChannelInfoByUserId(userId);
	}

}
