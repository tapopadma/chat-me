package chat.me.dto;

import java.io.Serializable;

public class MessageChannelTrnDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3162670012009241852L;

	private Long messageChannelTrnId;
	
	private String messageId;
	
	private String channelId;

	public MessageChannelTrnDto(Long messageChannelTrnId, String messageId, String channelId) {
		super();
		this.setMessageChannelTrnId(messageChannelTrnId);
		this.setMessageId(messageId);
		this.setChannelId(channelId);
	}

	public MessageChannelTrnDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getMessageChannelTrnId() {
		return messageChannelTrnId;
	}

	public void setMessageChannelTrnId(Long messageChannelTrnId) {
		this.messageChannelTrnId = messageChannelTrnId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	
	
}
