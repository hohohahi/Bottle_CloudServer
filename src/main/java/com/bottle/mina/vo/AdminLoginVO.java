package com.bottle.mina.vo;

import com.bottle.mina.constants.MinaConstants;

public class AdminLoginVO extends AbstractMessageVO {	
	private static final long serialVersionUID = 1L;
	private String username = "";
	private String password = "";

	public AdminLoginVO() {
		super.setMessageType(MinaConstants.MinaMessageType._MinaMessage_Type_AdminLogin.getId());
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

	@Override
	public String toString() {
		return "AdminLoginVO [username=" + username + ", password=" + password + "]";
	}
}