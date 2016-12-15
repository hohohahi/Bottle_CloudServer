package com.shishuo.cms.entity.vo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TemplateVO {
	private long id = 0L;
	private String name = "";
	private String barCode = "";
	private double price = 0.0d;
	private long isMetal = 0L; //1, yes; 0, no
	private long weight = 0L;
	private long posNum = 0L;
	private List<PositionInfoVO> positionInfoList = new ArrayList<PositionInfoVO>();
	private long status = 0L;
	private String description = "";
	private Timestamp createdDate = new Timestamp(0L);
	
	private long createdBy = 0L;
	private Timestamp modifiedDate = new Timestamp(0L);
	private long modifiedBy = 0L;
	private String createTimeStr = "";
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
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getIsMetal() {
		return isMetal;
	}
	public void setIsMetal(long isMetal) {
		this.isMetal = isMetal;
	}
	public long getWeight() {
		return weight;
	}
	public void setWeight(long weight) {
		this.weight = weight;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public long getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public long getPosNum() {
		return posNum;
	}
	public void setPosNum(long posNum) {
		this.posNum = posNum;
	}
	public List<PositionInfoVO> getPositionInfoList() {
		return positionInfoList;
	}
	public void setPositionInfoList(List<PositionInfoVO> positionInfoList) {
		this.positionInfoList = positionInfoList;
	}
	
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	@Override
	public String toString() {
		return "TemplateVO [id=" + id + ", name=" + name + ", barCode=" + barCode + ", price=" + price + ", isMetal="
				+ isMetal + ", weight=" + weight + ", posNum=" + posNum + ", positionInfoList=" + positionInfoList
				+ ", status=" + status + ", description=" + description + ", createdDate=" + createdDate
				+ ", createdBy=" + createdBy + ", modifiedDate=" + modifiedDate + ", modifiedBy=" + modifiedBy + "]";
	}
}
