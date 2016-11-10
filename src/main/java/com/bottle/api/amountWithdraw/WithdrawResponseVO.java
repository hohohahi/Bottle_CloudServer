package com.bottle.api.amountWithdraw;

public class WithdrawResponseVO {
	private boolean isOk;
	private String reason;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
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
			.append(", reason = ").append(reason)
			.append(", details = ").append(details).append(" ,originReturnMsg = ").append(originReturnMsg).append(" ]");
		
		return sbMsg.toString();
	}
}
