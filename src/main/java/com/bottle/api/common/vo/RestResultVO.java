package com.bottle.api.common.vo;

import com.bottle.api.common.constants.IWebServiceConstants;

public class RestResultVO {
	private long errorCode = IWebServiceConstants.enumWebServiceErrorCOde._errorCode_OK.getId();
	private String errorMessage = IWebServiceConstants.enumWebServiceErrorCOde._errorCode_OK.getMessage();
	private String extraMessage = "";
	private Object data = new SerializableObject();
	
	public RestResultVO(){
		
	}
	
	public RestResultVO(IWebServiceConstants.RestServiceExceptionEnum errorEnum){
		this.errorCode = errorEnum.getErrorCode();
		this.errorMessage = errorEnum.getErrorMessage();
	}
	
	public void assignExceptionEnum(final IWebServiceConstants.RestServiceExceptionEnum errorEnum){
		this.errorCode = errorEnum.getErrorCode();
		this.errorMessage = errorEnum.getErrorMessage();
		this.extraMessage = errorEnum.getExtraMessage();
	}
	
	public long getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getExtraMessage() {
		return extraMessage;
	}

	public void setExtraMessage(String extraMessage) {
		this.extraMessage = extraMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "RestResultVO [errorCode=" + errorCode + ", errorMessage="
				+ errorMessage + ", extraMessage=" + extraMessage + ", data=" + data+ "]";
	}
}
