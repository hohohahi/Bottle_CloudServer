package com.bottle.common.redisCache.common;

public enum RedisKeyEnum {
	
	UserSession("userSession"),
	Partner("partner"),
	PartnerSiteCatalog("PartnerSiteCatalog"),
	PartnerSite("PartnerSite");
	private final String info;

	RedisKeyEnum(final String info){
		this.info=info;
	}
	
	public String getInfo() {
		return info;
	}
}
