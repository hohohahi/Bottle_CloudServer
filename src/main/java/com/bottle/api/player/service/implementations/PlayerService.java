package com.bottle.api.player.service.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.api.common.constants.IWebServiceConstants;
import com.bottle.api.common.exception.MyAPIRuntimeException;
import com.bottle.api.player.dao.IPhoneAndCodeMapDAO;
import com.bottle.api.player.dao.IPlayerDAO;
import com.bottle.api.player.service.interfaces.IPlayerService;
import com.bottle.api.player.service.interfaces.ISMSCodeSender;
import com.bottle.api.player.vo.PhoneAndCodeMapVO;
import com.bottle.api.player.vo.PlayerVO;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.redisCache.userSession.IPlayerSessionCacheService;

@Service
public class PlayerService extends AbstractBaseBean implements IPlayerService {
	@Autowired
	private IPhoneAndCodeMapDAO phoneAndCodeMapDAO;
	
	@Autowired
	private IPlayerDAO playerDAO;
	
	@Autowired
	private ISMSCodeSender smsCodeSender;
	
	@Autowired
	private IPlayerSessionCacheService sessionService;
	
	@Override
	public void verifySMSCode(long phoneNumber, String smsCode) {
		if (false == isMobile(phoneNumber)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_PhoneNum_Invalid);
		}

		final PhoneAndCodeMapVO mapVO = phoneAndCodeMapDAO.selectOne_ByPhoneNumber(phoneNumber);
		if (null == mapVO){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_No_SMSCode_Existed_Under_Phone);
		}
		
		final String actualCode = mapVO.getCode();
		if (false == actualCode.equals(smsCode)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Wrong_SMSCode);
		}
	}
	
	public boolean isMobile(final long phoneNum) {
		return Pattern.matches(IWebServiceConstants.REGEX_MOBILE, phoneNum + "");
	}

	@Override
	public void register(PlayerVO vo) {
		final long phoneNumber = vo.getPhoneNumber();
		final String smsCode = vo.getSmsCode();
		
		final PlayerVO playerVO = playerDAO.selectOne_ByPhoneNumber(phoneNumber);
		if (null != playerVO) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Player_Already_Existed);			
		}
		
		verifySMSCode(phoneNumber, smsCode);
		
		try {
			playerDAO.insert(vo);
			phoneAndCodeMapDAO.deleteByPhoneNum(phoneNumber);
		} catch (Exception e) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_DB_ERROR, e.getMessage());
		}		
	}

	@Override
	public void applySMSCode(final long phoneNumber) {
		super.debugLog("applySMSCode: verify phone number. phoneNumber:" + phoneNumber);
		if (false == isMobile(phoneNumber)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_PhoneNum_Invalid);
		}
		
		super.debugLog("applySMSCode: send sms code");
		String smsCode = "";
		try {
			smsCode = smsCodeSender.sendRandomSMSCode(phoneNumber);
		} catch (Exception e) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_SMSCode_ERROR, e.getMessage());
		}
		super.debugLog("applySMSCode: send sms code over. smsCode:" + smsCode);
		
		try {
			phoneAndCodeMapDAO.deleteByPhoneNum(phoneNumber);		
			final PhoneAndCodeMapVO mapVO = new PhoneAndCodeMapVO();
			mapVO.setPhoneNumber(phoneNumber);
			mapVO.setCode(smsCode);
			phoneAndCodeMapDAO.insert(mapVO);
		} catch (Exception e) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_DB_ERROR, e.getMessage());
		}
		
		super.debugLog("applySMSCode: insert phone and code map over");
	}

	@Override
	public PlayerVO login(PlayerVO vo) {
		final long phoneNumber = vo.getPhoneNumber();
		if (false == isMobile(phoneNumber)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_PhoneNum_Invalid);
		}
		
		final PlayerVO playerVO = playerDAO.selectOne_ByPhoneNumber(phoneNumber);
		if (null == playerVO) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Player_Not_Existed);			
		}
		
		final String password_DB = playerVO.getPassword();
		final String password_Request = vo.getPassword();
		if (false == password_DB.equals(password_Request)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Wrong_Password);
		}
		
		final PlayerVO playerVO_Cache = sessionService.getPlayerVOByPhoneNumber(phoneNumber);
		if (true == sessionService.isPlayerLogined(playerVO_Cache, phoneNumber)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Player_Already_Login, playerVO_Cache.toString());
		}
		
		//add to map
		sessionService.setPlayerSession(phoneNumber, playerVO);
		return playerVO;
	}

	@Override
	public List<PlayerVO> selectAll() {
		List<PlayerVO> playerVOList = new ArrayList<PlayerVO>();
		try {
			playerVOList = playerDAO.selectAll();
		} catch (Exception e) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_DB_ERROR);
		}
		
		return playerVOList;
	}
}
