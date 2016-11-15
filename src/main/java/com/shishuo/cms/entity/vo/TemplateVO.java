package com.shishuo.cms.entity.vo;

import java.sql.Timestamp;

public class TemplateVO {
	long id = 0L;
	String name = "";
	String barCode = "";
	double price = 0.0d;
	long isMetal = 0L; //1, yes; 0, no
	long weight = 0L;
	String imageCharacteristic = "";
	long status = 0L;
	String description = "";
	Timestamp createdDate = new Timestamp(0L);
	long createdBy = 0L;
	Timestamp modifiedDate = new Timestamp(0L);
	long modifiedBy = 0L;
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
	public String getImageCharacteristic() {
		return imageCharacteristic;
	}
	public void setImageCharacteristic(String imageCharacteristic) {
		this.imageCharacteristic = imageCharacteristic;
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
	@Override
	public String toString() {
		return "TemplateVO [id=" + id + ", name=" + name + ", barCode="
				+ barCode + ", price=" + price + ", isMetal=" + isMetal
				+ ", weight=" + weight + ", imageCharacteristic="
				+ imageCharacteristic + ", status=" + status + ", description="
				+ description + ", createdDate=" + createdDate + ", createdBy="
				+ createdBy + ", modifiedDate=" + modifiedDate
				+ ", modifiedBy=" + modifiedBy + "]";
	}
}
