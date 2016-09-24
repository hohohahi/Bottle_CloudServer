package com.bottle.api.common.exception;

import com.bottle.api.common.constants.IWebServiceConstants;

public class MyAPIRuntimeException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private IWebServiceConstants.RestServiceExceptionEnum errorDefinitionEnum;
	
	
	public IWebServiceConstants.RestServiceExceptionEnum getErrorDefinitionEnum() {
		return errorDefinitionEnum;
	}

	public void setErrorDefinitionEnum(
			IWebServiceConstants.RestServiceExceptionEnum errorDefinitionEnum) {
		this.errorDefinitionEnum = errorDefinitionEnum;
	}


	public MyAPIRuntimeException(final IWebServiceConstants.RestServiceExceptionEnum error){
		errorDefinitionEnum = error;
	}
	
	public MyAPIRuntimeException(final IWebServiceConstants.RestServiceExceptionEnum error, final String extraMessage){
		errorDefinitionEnum = error;
		errorDefinitionEnum.setExtraMessage(extraMessage);
	}
}
