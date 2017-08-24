package com.bottle.api.player.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class LastCheckRecordStatisticVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private long sum = 0L;
	private String location = "";
	private Map<String, Long> resultMap = new HashMap<String, Long>();
	private double returnMoneySum = 0.0d;
	private long donateBottleSum = 0L;
	private Timestamp createdDate = new Timestamp(0L);
	public long getSum() {
		return sum;
	}
	public void setSum(long sum) {
		this.sum = sum;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Map<String, Long> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, Long> resultMap) {
		this.resultMap = resultMap;
	}
	public double getReturnMoneySum() {
		return returnMoneySum;
	}
	public void setReturnMoneySum(double returnMoneySum) {
		this.returnMoneySum = returnMoneySum;
	}
	public long getDonateBottleSum() {
		return donateBottleSum;
	}
	public void setDonateBottleSum(long donateBottleSum) {
		this.donateBottleSum = donateBottleSum;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
	@Override
	public String toString() {
		return "LastCheckRecordVO [sum=" + sum + ", location=" + location + ", resultMap=" + resultMap
				+ ", returnMoneySum=" + returnMoneySum + ", donateBottleSum=" + donateBottleSum + ", createdDate="
				+ createdDate + "]";
	}
}
