package chat.me.entity;

import chat.me.dto.MessageTrnDto;

public class MessageMiscellaneousInfoEntity {
	
	private MessageTrnDto messageTrnDto;
	
	private boolean userTyping;
	
	public MessageMiscellaneousInfoEntity(MessageTrnDto messageTrnDto, boolean userTyping) {
		super();
		this.messageTrnDto = messageTrnDto;
		this.userTyping = userTyping;
	}

	public MessageMiscellaneousInfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageTrnDto getMessageTrnDto() {
		return messageTrnDto;
	}

	public void setMessageTrnDto(MessageTrnDto messageTrnDto) {
		this.messageTrnDto = messageTrnDto;
	}

	public boolean isUserTyping() {
		return userTyping;
	}

	public void setUserTyping(boolean userTyping) {
		this.userTyping = userTyping;
	}
	
}
