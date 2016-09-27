package com.bottle.api.bottle.vo;

import java.io.Serializable;

public class BottleVO implements Serializable{
	private static final long serialVersionUID = -7333754486792974040L;
	long id = 0L;
	String name = "";
	long status = 0L;
	String location = "";
	String identifier = "";
	
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	@Override
	public String toString() {
		return "BottleVO [id=" + id + ", name=" + name + ", status=" + status
				+ ", location=" + location + ", identifier=" + identifier + "]";
	}		
}
