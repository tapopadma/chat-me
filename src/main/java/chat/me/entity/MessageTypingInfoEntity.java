package chat.me.entity;

import chat.me.dto.MessageTrnDto;

public class MessageTypingInfoEntity {
	
	private MessageTrnDto messageTrnDto;
	
	private boolean userTyping;
	
	public MessageTypingInfoEntity(MessageTrnDto messageTrnDto, boolean userTyping) {
		super();
		this.messageTrnDto = messageTrnDto;
		this.userTyping = userTyping;
	}

	public MessageTypingInfoEntity() {
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
