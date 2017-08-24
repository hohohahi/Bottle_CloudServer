package com.bottle.api.player.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.bottle.api.ui.vo.PlayerCheckRecordVO;

public class PlayerVO implements Serializable{
	private static final long serialVersionUID = 468110010958412844L;
	long id = 0L;
	String name = "";
	long status = 0L;
	long role = 1L;  //common player by default. 1 -- common player; 2 -- Anonymous Recipients; 3 -- Admin
	long phoneNumber = 0L;
	String password = "";
	double amount = 0.0d;
	private long score = 0L;	
	String smsCode = "";
	private Timestamp createdDate = new Timestamp(0L);
	
	private long totalBottleNum = 0L;
	private long totalCheckNum = 0L;
	private double totalAmount = 0.0d;
	private long totalReturnMoneyBottleNum = 0L;
	private long totalReturnMoneyCheckNum = 0L;
	private double totalReturnMoneyAmount = 0.0d;
	private long totalDonateBottleNum = 0L;
	private long totalDonateCheckNum = 0L;
	private double totalDonateAmount = 0.0d;
		
	private long totalSavingOilSum = 0L;
	private long totalSavingCarbonDioxideSum = 0L;
	
	private List<PlayerCheckRecordVO> checkRecordVOList = new ArrayList<PlayerCheckRecordVO>();
	private LastCheckRecordStatisticVO lastCheckRecord = new LastCheckRecordStatisticVO();
	
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
	public long getRole() {
		return role;
	}
	public void setRole(long role) {
		this.role = role;
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
	public long getScore() {
		return score;
	}
	public void setScore(long score) {
		this.score = score;
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
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
	public long getTotalBottleNum() {
		return totalBottleNum;
	}
	public void setTotalBottleNum(long totalBottleNum) {
		this.totalBottleNum = totalBottleNum;
	}
	public long getTotalCheckNum() {
		return totalCheckNum;
	}
	public void setTotalCheckNum(long totalCheckNum) {
		this.totalCheckNum = totalCheckNum;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public long getTotalReturnMoneyBottleNum() {
		return totalReturnMoneyBottleNum;
	}
	public void setTotalReturnMoneyBottleNum(long totalReturnMoneyBottleNum) {
		this.totalReturnMoneyBottleNum = totalReturnMoneyBottleNum;
	}
	public long getTotalReturnMoneyCheckNum() {
		return totalReturnMoneyCheckNum;
	}
	public void setTotalReturnMoneyCheckNum(long totalReturnMoneyCheckNum) {
		this.totalReturnMoneyCheckNum = totalReturnMoneyCheckNum;
	}
	public double getTotalReturnMoneyAmount() {
		return totalReturnMoneyAmount;
	}
	public void setTotalReturnMoneyAmount(double totalReturnMoneyAmount) {
		this.totalReturnMoneyAmount = totalReturnMoneyAmount;
	}
	public long getTotalDonateBottleNum() {
		return totalDonateBottleNum;
	}
	public void setTotalDonateBottleNum(long totalDonateBottleNum) {
		this.totalDonateBottleNum = totalDonateBottleNum;
	}
	public long getTotalDonateCheckNum() {
		return totalDonateCheckNum;
	}
	public void setTotalDonateCheckNum(long totalDonateCheckNum) {
		this.totalDonateCheckNum = totalDonateCheckNum;
	}
	public double getTotalDonateAmount() {
		return totalDonateAmount;
	}
	public void setTotalDonateAmount(double totalDonateAmount) {
		this.totalDonateAmount = totalDonateAmount;
	}
	public LastCheckRecordStatisticVO getLastCheckRecord() {
		return lastCheckRecord;
	}
	public void setLastCheckRecord(LastCheckRecordStatisticVO lastCheckRecord) {
		this.lastCheckRecord = lastCheckRecord;
	}
	
	public long getTotalSavingOilSum() {
		return totalSavingOilSum;
	}
	public void setTotalSavingOilSum(long totalSavingOilSum) {
		this.totalSavingOilSum = totalSavingOilSum;
	}
	public long getTotalSavingCarbonDioxideSum() {
		return totalSavingCarbonDioxideSum;
	}
	public void setTotalSavingCarbonDioxideSum(long totalSavingCarbonDioxideSum) {
		this.totalSavingCarbonDioxideSum = totalSavingCarbonDioxideSum;
	}
	@Override
	public String toString() {
		return "PlayerVO [id=" + id + ", name=" + name + ", status=" + status + ", role=" + role + ", phoneNumber="
				+ phoneNumber + ", password=" + password + ", amount=" + amount + ", score=" + score + ", smsCode="
				+ smsCode + ", createdDate=" + createdDate + ", totalBottleNum=" + totalBottleNum + ", totalCheckNum="
				+ totalCheckNum + ", totalAmount=" + totalAmount + ", totalReturnMoneyBottleNum="
				+ totalReturnMoneyBottleNum + ", totalReturnMoneyCheckNum=" + totalReturnMoneyCheckNum
				+ ", totalReturnMoneyAmount=" + totalReturnMoneyAmount + ", totalDonateBottleNum="
				+ totalDonateBottleNum + ", totalDonateCheckNum=" + totalDonateCheckNum + ", totalDonateAmount="
				+ totalDonateAmount + ", totalSavingOilSum=" + totalSavingOilSum + ", totalSavingCarbonDioxideSum="
				+ totalSavingCarbonDioxideSum + ", checkRecordVOList=" + checkRecordVOList + ", lastCheckRecord="
				+ lastCheckRecord + "]";
	}
}
