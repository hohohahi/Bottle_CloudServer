package com.bottle.common.redisCache.userSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.api.player.vo.PlayerVO;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.redisCache.common.IRedisConstants;
import com.bottle.common.redisCache.common.SerializeUtil;
import com.bottle.common.redisCache.interfaces.IRedisClient;

@Service
public class SessionCacheSerivce extends AbstractBaseBean implements ISessionCacheService{
	@Autowired
	private IRedisClient redisClient;	
	
	public IRedisClient getRedisClient() {
		return redisClient;
	}
	public void setRedisClient(IRedisClient redisClient) {
		this.redisClient = redisClient;
	}
		
	@Override
	public void removePlayerSession(final long phoneNumber) {
		try {
			redisClient.remove(phoneNumber + "");
			super.debugLog("removeUserSession: sessionid:" + phoneNumber);
		} catch (Exception e) {
			super.logErrorAndStack(e, e.getMessage());
		}
	}

	@Override
	public void setPlayerSession(final long phoneNumber, final PlayerVO vo) {
		
		try {
			redisClient.write_ByDefaultExpireTime(phoneNumber + "", SerializeUtil.serialize(vo));
		} catch (Exception e) {
			super.logErrorAndStack(e, e.getMessage());
		}
	}

	@Override
	public PlayerVO getPlayerVOByPhoneNumber(final long phoneNumber) {
		PlayerVO result = new PlayerVO();
		
		try {
			byte[] byteArray = redisClient.readByte(phoneNumber + "");
			if(null == byteArray){
				throw new NullPointerException("byteArray is null.");
			}
			
			Object obj = SerializeUtil.unserialize(byteArray);
			if(null == obj){
				throw new NullPointerException("obj is null.");
			}
			
			if (false == (obj instanceof PlayerVO)) {
				throw new RuntimeException("obj is not instance of PlayerVO.");
			}
			
			result = (PlayerVO) obj;
		} catch (Exception e) {
			super.logErrorAndStack(e, e.getMessage());
		}

		return result;
	}
	
	@Override
	public boolean isPlayerLogined(final PlayerVO cacheVO, final long phoneNumber) {
		boolean isLogined = false;
		
		if (null == cacheVO) {
			isLogined = false;
		}
		else {
			final long cachedPhoneNumber = cacheVO.getPhoneNumber();
			if (phoneNumber == cachedPhoneNumber){
				isLogined = true;
			}
			else {
				isLogined = false;
			}
		}
		
		return isLogined;
	}
	
	@Override
	public boolean isBottleMounted(String identifier) {
		boolean isMounted = false;
		if (null == redisClient.readByte(identifier)){
			isMounted = false;
		}
		else {
			isMounted = true;
		}

		return isMounted;
	}
	@Override
	public void mount(String identifier, long phoneNumber) {
		try {
			redisClient.write(identifier, IRedisConstants._Bottle_Mount_ExpireTime_, SerializeUtil.serialize(phoneNumber));
		} catch (Exception e) {
			super.logErrorAndStack(e, e.getMessage());
		}
	}
	
	@Override
	public void unmount(String identifier) {
		try {
			redisClient.remove(identifier);;
		} catch (Exception e) {
			super.logErrorAndStack(e, e.getMessage());
		}
	}
	
	@Override
	public long getPhoneNumberByIdentifier(String identifier) {		
		byte[] byteArray = redisClient.readByte(identifier);
		if(null == byteArray){
			throw new NullPointerException("byteArray is null.");
		}
		
		Object obj = SerializeUtil.unserialize(byteArray);
		if(null == obj){
			throw new NullPointerException("obj is null.");
		}
		
		if (false == (obj instanceof Long)
				&& false == (obj instanceof Integer)) {
			throw new RuntimeException("obj is not instance of Long, or Integer.");
		}
		
		long phoneNumber = 0L;
		if (true == (obj instanceof Long)){
			phoneNumber = (Long)obj;
		}
		if (true == (obj instanceof Integer)){
			phoneNumber = (Integer)obj;
		}
		
		return phoneNumber;
	}
	@Override
	public void removeByKey(String identifier) {
		try {
			redisClient.remove(identifier );
		} catch (Exception e) {
			super.logErrorAndStack(e, e.getMessage());
		}		
	}	
}