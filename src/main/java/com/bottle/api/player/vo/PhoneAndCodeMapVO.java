package com.bottle.api.player.vo;

public class PhoneAndCodeMapVO {
	long id = 0L;
	long phoneNumber = 0L;
	String code = "";
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
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
		return "PhoneAndCodeMapVO [id=" + id + ", phoneNumber=" + phoneNumber
				+ ", code=" + code + "]";
	}
}
