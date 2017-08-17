package com.bottle.api.player.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bottle.api.ui.vo.PlayerCheckRecordVO;

public class PlayerVO implements Serializable{
	private static final long serialVersionUID = 468110010958412844L;
	long id = 0L;
	String name = "";
	long status = 0L;
	long phoneNumber = 0L;
	String password = "";
	double amount = 0.0d;
	String smsCode = "";
	private List<PlayerCheckRecordVO> checkRecordVOList = new ArrayList<PlayerCheckRecordVO>();
	
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
	public List<PlayerCheckRecordVO> getCheckRecordVOList() {
		return checkRecordVOList;
	}
	public void setCheckRecordVOList(List<PlayerCheckRecordVO> checkRecordVOList) {
		this.checkRecordVOList = checkRecordVOList;
	}
	
	@Override
	public String toString() {
		return "PlayerVO [id=" + id + ", name=" + name + ", status=" + status
				+ ", phoneNumber=" + phoneNumber + ", password=" + password
				+ ", amount=" + amount  + ", smsCode=" + smsCode + "]";
	}
}
