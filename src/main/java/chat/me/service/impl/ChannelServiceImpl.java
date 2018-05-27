package chat.me.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
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
	public List<ChannelInfoEntity> create(List<ChannelInfoEntity> entityList) {
		if(entityList.isEmpty())
			return new ArrayList<ChannelInfoEntity>();
		String newChannelId = UUID.randomUUID().toString();
		ChannelMstDto dto = new ChannelMstDto();
		dto.setChannelId(newChannelId);
		dto.setChannelName(entityList.get(0).getChannelName());
		dto = channelDaoImpl.insert(dto);
		Timestamp creationDate = dto.getChannelCreationDate();
		List<ChannelUserMstDto> dtoList = new ArrayList<>();
		entityList.stream().forEach(entity->{
			ChannelUserMstDto dto1 = new ChannelUserMstDto();
			dto1.setChannelId(newChannelId);
			dto1.setUserId(entity.getUserId());
			dto1.setUserType(entity.getUserType());
			dtoList.add(dto1);
			entity.setChannelId(newChannelId);
			entity.setChannelCreationDate(creationDate);
		});
		channelDaoImpl.insertInBatch(dtoList);
		return entityList;
	}

	@Override
	public List<ChannelInfoEntity> getAll(String userId) {
		return channelDaoImpl.getAllChannelInfoByUserId(userId);
	}

}
