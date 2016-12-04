package com.bottle.backoffice.admin.vo;

public class AdminRoleMapVO {
	private long id = 0L;
	private long adminId = 0L;
	private long roleId = 0L;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAdminId() {
		return adminId;
	}
	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	@Override
	public String toString() {
		return "AdminRoleMapVO [id=" + id + ", adminId=" + adminId + ", roleId=" + roleId + "]";
	}
}
