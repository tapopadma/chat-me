package chat.me.entity;

import java.util.List;

import chat.me.dto.MessageTrnDto;

public class MessageTrnInfoEntity {

	private boolean markAllMessageAsRead;
	
	private List<MessageTrnDto> messageTrnDtoList;

	public MessageTrnInfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isMarkAllMessageAsRead() {
		return markAllMessageAsRead;
	}

	public void setMarkAllMessageAsRead(boolean markAllMessageAsRead) {
		this.markAllMessageAsRead = markAllMessageAsRead;
	}

	public List<MessageTrnDto> getMessageTrnDtoList() {
		return messageTrnDtoList;
	}

	public void setMessageTrnDtoList(List<MessageTrnDto> messageTrnDtoList) {
		this.messageTrnDtoList = messageTrnDtoList;
	}

	public MessageTrnInfoEntity(boolean markAllMessageAsRead, List<MessageTrnDto> messageTrnDtoList) {
		super();
		this.markAllMessageAsRead = markAllMessageAsRead;
		this.messageTrnDtoList = messageTrnDtoList;
	}

	
	
}
