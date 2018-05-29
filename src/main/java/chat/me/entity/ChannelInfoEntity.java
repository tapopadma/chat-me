package chat.me.entity;

import java.util.List;

import chat.me.dto.ChannelMstDto;
import chat.me.dto.ChannelUserMstDto;

public class ChannelInfoEntity {

	private ChannelMstDto channelMstDto;
	private List<ChannelUserMstDto> channelUserMstDtoList;
	
	
	public ChannelMstDto getChannelMstDto() {
		return channelMstDto;
	}
	public void setChannelMstDto(ChannelMstDto channelMstDto) {
		this.channelMstDto = channelMstDto;
	}
	public List<ChannelUserMstDto> getChannelUserMstDtoList() {
		return channelUserMstDtoList;
	}
	public void setChannelUserMstDtoList(List<ChannelUserMstDto> channelUserMstDtoList) {
		this.channelUserMstDtoList = channelUserMstDtoList;
	}
	public ChannelInfoEntity(ChannelMstDto channelMstDto, List<ChannelUserMstDto> channelUserMstDtoList) {
		super();
		this.channelMstDto = channelMstDto;
		this.channelUserMstDtoList = channelUserMstDtoList;
	}
	public ChannelInfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
