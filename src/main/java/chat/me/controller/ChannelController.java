package chat.me.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import chat.me.dto.ChannelMstDto;
import chat.me.entity.ChannelInfoEntity;
import chat.me.service.impl.ChannelServiceImpl;

@Controller
@RequestMapping("/channel")
public class ChannelController {

	@Autowired
	private ChannelServiceImpl channelServiceImpl;
	
	@ResponseBody
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public List<ChannelMstDto> create(ChannelInfoEntity entity) {
		return channelServiceImpl.create(entity);
	}
	
	@ResponseBody
	@RequestMapping(value="/getAll", method=RequestMethod.POST)
	public List<ChannelMstDto> getAll(String username){
		return channelServiceImpl.getAll(username);
	}
}
