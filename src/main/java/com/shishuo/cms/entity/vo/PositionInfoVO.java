package com.shishuo.cms.entity.vo;

public class PositionInfoVO {
	private long x = 0L;
	private long y = 0L;
	
	public PositionInfoVO() {
		
	}
	
	public PositionInfoVO(long x, long y) {
		this.x = x;
		this.y = y;
	}
	
	public long getX() {
		return x;
	}
	public long getY() {
		return y;
	}
	
	public void setX(long x) {
		this.x = x;
	}

	public void setY(long y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "PositionInfo [x=" + x + ", y=" + y + ", getX()=" + getX() + ", getY()=" + getY() + "]";
	}
}
