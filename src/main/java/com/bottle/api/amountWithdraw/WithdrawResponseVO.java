package com.bottle.api.amountWithdraw;

public class WithdrawResponseVO {
	private boolean isOk;
	private String errorMsg;
	private String details;
	private String originReturnMsg;
	
	
	public String getOriginReturnMsg() {
		return originReturnMsg;
	}
	public void setOriginReturnMsg(String originReturnMsg) {
		this.originReturnMsg = originReturnMsg;
	}
	public boolean isOk() {
		return isOk;
	}
	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	
	public String toString(){
		StringBuilder sbMsg=new StringBuilder();
		sbMsg.append("WithdrawResponseVO[  isOk = ").append(isOk)
			.append(", errorMsg = ").append(errorMsg)
			.append(", details = ").append(details).append(" ,originReturnMsg = ").append(originReturnMsg).append(" ]");
		
		return sbMsg.toString();
	}
}
