package com.bottle.api.common.vo;

import java.io.Serializable;

public class SerializableObject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long xo = 0L;

	public long getXo() {
		return xo;
	}

	public void setXo(long id) {
		this.xo = id;
	}

	@Override
	public String toString() {
		return "SerializableObject [xo=" + xo + "]";
	}
}
