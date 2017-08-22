package com.bottle.mina.vo;

import com.bottle.mina.constants.MinaConstants;

public class ServerMessageVO extends AbstractMessageVO {	
	private static final long serialVersionUID = 1L;
	private String identifier = "";
	private long subMessageType = MinaConstants.MinaMessageType._MinaMessage_Type_None.getId();
	private long phoneNumber = 0L;
	
	public ServerMessageVO() {
		
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public long getSubMessageType() {
		return subMessageType;
	}

	public void setSubMessageType(long subMessageType) {
		this.subMessageType = subMessageType;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "SubscriptionVO [identifier=" + identifier + "]";
	}
}