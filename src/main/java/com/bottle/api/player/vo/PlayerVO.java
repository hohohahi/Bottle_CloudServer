package com.bottle.api.player.vo;

public class PlayerVO {
	long id = 0L;
	String name = "";
	long status = 0L;
	long phoneNumber = 0L;
	String password = "";
	double amount = 0.0d;
	String smsCode = "";
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	@Override
	public String toString() {
		return "PlayerVO [id=" + id + ", name=" + name + ", status=" + status
				+ ", phoneNumber=" + phoneNumber + ", password=" + password
				+ ", amount=" + amount  + ", smsCode=" + smsCode + "]";
	}
}
