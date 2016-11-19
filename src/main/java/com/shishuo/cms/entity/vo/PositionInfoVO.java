package com.shishuo.cms.entity.vo;

public class PositionInfoVO {
	private long x = 0L;
	private long y = 0L;
	
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
	
	@Override
	public String toString() {
		return "PositionInfo [x=" + x + ", y=" + y + ", getX()=" + getX() + ", getY()=" + getY() + "]";
	}
}
