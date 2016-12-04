package com.bottle.backoffice.admin.vo;

public class RoleVO {
	private long id = 0L;
	private String name = "";
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
	
	@Override
	public String toString() {
		return "RoleVO [id=" + id + ", name=" + name + "]";
	}
}
