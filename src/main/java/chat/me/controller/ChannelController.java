package chat.me.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import chat.me.entity.ChannelInfoEntity;
import chat.me.service.spec.ChannelService;

@Controller
@RequestMapping("/channel")
public class ChannelController {

	@Autowired
	private ChannelService channelService;
	
	@ResponseBody
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ChannelInfoEntity create(@RequestBody ChannelInfoEntity entity) {
		return channelService.create(entity);
	}
	
	@ResponseBody
	@RequestMapping(value="/getAll", method=RequestMethod.POST)
	public List<ChannelInfoEntity> getAll(@RequestBody String userId){
		return channelService.getAll(userId);
	}
}
