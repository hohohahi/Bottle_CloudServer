package com.bottle.api.ui.vo;

public class CheckRecordVO {
	private long resultId = 0L;
	private long orderIndex = 0;
	private long templateId = 0L;
	private String templateName = "";
	private double price = 0.0d;
	
	public long getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(long orderIndex) {
		this.orderIndex = orderIndex;
	}
	public long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}	
	public long getResultId() {
		return resultId;
	}
	public void setResultId(long resultId) {
		this.resultId = resultId;
	}
	@Override
	public String toString() {
		return "CheckRecordVO [resultId=" + resultId + ", orderIndex=" + orderIndex + ", templateId=" + templateId
				+ ", templateName=" + templateName + ", price=" + price + "]";
	}
}
