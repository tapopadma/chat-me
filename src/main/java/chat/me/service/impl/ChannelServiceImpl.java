package chat.me.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chat.me.dao.impl.ChannelDaoImpl;
import chat.me.dto.ChannelMstDto;
import chat.me.entity.ChannelInfoEntity;
import chat.me.service.spec.ChannelService;

@Service
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	private ChannelDaoImpl channelDaoImpl;
	
	@Override
	public List<ChannelMstDto> create(ChannelInfoEntity entity) {
		List<ChannelMstDto> dtoList = new ArrayList<>();
		String newChannelId = UUID.randomUUID().toString();
		entity.getUserList().stream().forEach(username->{
			ChannelMstDto dto = new ChannelMstDto();
			dto.setChannelId(newChannelId);
			dto.setUsername(username);
			dto.setChannelName(entity.getChannelName());
			dtoList.add(dto);
		});
		return channelDaoImpl.insertInBatch(dtoList);
	}

	@Override
	public List<ChannelMstDto> getAll(String username) {
		return channelDaoImpl.getAllDtoByUsername(username);
	}

}
