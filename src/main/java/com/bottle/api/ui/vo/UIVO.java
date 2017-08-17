package com.bottle.api.ui.vo;

import java.util.ArrayList;
import java.util.List;

public class UIVO {
	private String identifier = "";
	private long phoneNumber = 0L;
	private double amount = 0.0d;
	private String username = "";
	private String password = "";
	private long templateId = 0L;
	private List<CheckRecordVO> checkRecordList = new ArrayList<CheckRecordVO>();
	
	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<CheckRecordVO> getCheckRecordList() {
		return checkRecordList;
	}

	public void setCheckRecordList(List<CheckRecordVO> checkRecordList) {
		this.checkRecordList = checkRecordList;
	}
}
