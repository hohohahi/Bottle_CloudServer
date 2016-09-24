package com.bottle.api.player.service.implementations;

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

@Service
public class PlayerService extends AbstractBaseBean implements IPlayerService {
	@Autowired
	private IPhoneAndCodeMapDAO phoneAndCodeMapDAO;
	
	@Autowired
	private IPlayerDAO playerDAO;
	
	@Autowired
	private ISMSCodeSender smsCodeSender;
	
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
}
