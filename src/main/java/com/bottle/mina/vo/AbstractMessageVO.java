package com.bottle.mina.vo;

import java.io.Serializable;

import com.bottle.mina.constants.MinaConstants;

public abstract class AbstractMessageVO implements Serializable{
	protected static final long serialVersionUID = 1L;
	private long messageType = MinaConstants.MinaMessageType._MinaMessage_Type_None.getId();
	
	public long getMessageType() {
		return messageType;
	}
	public void setMessageType(long messageType) {
		this.messageType = messageType;
	}
}