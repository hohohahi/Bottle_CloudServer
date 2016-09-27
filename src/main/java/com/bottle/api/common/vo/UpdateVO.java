package com.bottle.api.common.vo;


public class UpdateVO {
	long bottleStatus = 0L;
	String identifier = "";
	long phoneNumber = 0L;
	long mountStatus = 0L;
	public long getBottleStatus() {
		return bottleStatus;
	}
	public void setBottleStatus(long bottleStatus) {
		this.bottleStatus = bottleStatus;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public long getMountStatus() {
		return mountStatus;
	}
	public void setMountStatus(long mountStatus) {
		this.mountStatus = mountStatus;
	}
	@Override
	public String toString() {
		return "UpdateVO [bottleStatus=" + bottleStatus + ", identifier="
				+ identifier + ", phoneNumber=" + phoneNumber
				+ ", mountStatus=" + mountStatus + "]";
	}
}
