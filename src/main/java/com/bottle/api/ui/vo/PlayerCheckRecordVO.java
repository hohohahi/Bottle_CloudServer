package com.bottle.api.ui.vo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PlayerCheckRecordVO {
	private long resultId = 0L;
	private long phoneNumber = 0;
	private Timestamp createdDate = new Timestamp(0L);
	private List<CheckRecordVO> checkResultVOList = new ArrayList<CheckRecordVO>();
	
	public long getResultId() {
		return resultId;
	}
	public void setResultId(long resultId) {
		this.resultId = resultId;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}	
	public List<CheckRecordVO> getCheckResultVOList() {
		return checkResultVOList;
	}
	public void setCheckResultVOList(List<CheckRecordVO> checkResultVOList) {
		this.checkResultVOList = checkResultVOList;
	}
	
	@Override
	public String toString() {
		return "PlayerCheckRecordVO [resultId=" + resultId + ", phoneNumber=" + phoneNumber + ", createdDate="
				+ createdDate + "]";
	}
}
