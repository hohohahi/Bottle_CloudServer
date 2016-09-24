package com.bottle.api.player.vo;

public class VerificationVO {
	long phoneNumber = 0L;
	String code = "";
	
	public long getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return "VerificationVO [phoneNumber=" + phoneNumber + ", code=" + code
				+ "]";
	}
}
