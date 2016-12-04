package com.bottle.mina.vo;

import com.bottle.mina.constants.MinaConstants;

public class TemplateOperationVO extends AbstractMessageVO {	
	private static final long serialVersionUID = 1L;

	public TemplateOperationVO() {
		super.setMessageType(MinaConstants.MinaMessageType._MinaMessage_Type_TemplateOperaton.getId());
	}
}