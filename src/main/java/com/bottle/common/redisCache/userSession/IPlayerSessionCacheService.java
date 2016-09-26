package com.bottle.common.redisCache.userSession;

import com.bottle.api.player.vo.PlayerVO;

public interface IPlayerSessionCacheService {
	public void removePlayerSession(final long phoneNumber);
	public void setPlayerSession(final long phoneNumber, final PlayerVO vo);
	public PlayerVO getPlayerVOByPhoneNumber(final long phoneNumber);
	public boolean isPlayerLogined(final PlayerVO cacheVO, final long phoneNumber);
}
