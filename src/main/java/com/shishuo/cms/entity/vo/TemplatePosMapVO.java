package com.shishuo.cms.entity.vo;

public class TemplatePosMapVO {
	private long id = 0L;
	private long templateId = 0L;
	private long posOrder = 0L;
	private long xPos = 0L;
	private long yPos = 0L;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}
	public long getPosOrder() {
		return posOrder;
	}
	public void setPosOrder(long posOrder) {
		this.posOrder = posOrder;
	}
	public long getxPos() {
		return xPos;
	}
	public void setxPos(long xPos) {
		this.xPos = xPos;
	}
	public long getyPos() {
		return yPos;
	}
	public void setyPos(long yPos) {
		this.yPos = yPos;
	}
	@Override
	public String toString() {
		return "TemplatePosMapVO [id=" + id + ", templateId=" + templateId + ", posOrder=" + posOrder + ", xPos=" + xPos
				+ ", yPos=" + yPos + "]";
	}
}
