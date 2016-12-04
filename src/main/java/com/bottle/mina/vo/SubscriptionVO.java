package com.bottle.mina.vo;

import com.bottle.mina.constants.MinaConstants;

public class SubscriptionVO extends AbstractMessageVO {	
	private static final long serialVersionUID = 1L;
	private String identifier = "";

	public SubscriptionVO() {
		super.setMessageType(MinaConstants.MinaMessageType._MinaMessage_Type_Subscription.getId());
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return "SubscriptionVO [identifier=" + identifier + "]";
	}
}