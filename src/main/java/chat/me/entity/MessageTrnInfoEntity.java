package chat.me.entity;

import java.util.List;

public class MessageTrnInfoEntity {

	private boolean markAllMessageAsRead;
	
	private List<MessageWithDeliverystatusInfoEntity> messageTrnDtoList;
	
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

	public List<MessageWithDeliverystatusInfoEntity> getMessageTrnDtoList() {
		return messageTrnDtoList;
	}

	public void setMessageTrnDtoList(List<MessageWithDeliverystatusInfoEntity> messageTrnDtoList) {
		this.messageTrnDtoList = messageTrnDtoList;
	}

	public MessageTrnInfoEntity(boolean markAllMessageAsRead,
			List<MessageWithDeliverystatusInfoEntity> messageTrnDtoList) {
		super();
		this.markAllMessageAsRead = markAllMessageAsRead;
		this.messageTrnDtoList = messageTrnDtoList;
	}

}