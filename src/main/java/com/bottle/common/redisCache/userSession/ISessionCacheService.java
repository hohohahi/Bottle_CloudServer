package com.bottle.common.redisCache.userSession;

import com.bottle.api.player.vo.PlayerVO;

public interface ISessionCacheService {
	public void removePlayerSession(final long phoneNumber);
	public void setPlayerSession(final long phoneNumber, final PlayerVO vo);
	public PlayerVO getPlayerVOByPhoneNumber(final long phoneNumber);
	public boolean isPlayerLogined(final PlayerVO cacheVO, final long phoneNumber);
	public boolean isBottleMounted(final String identifier);
	public void mount(final String identifier, final long phoneNumber);
}
