package com.bottle.api.player.service.implementations;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.api.common.constants.IWebServiceConstants;
import com.bottle.api.common.exception.MyAPIRuntimeException;
import com.bottle.api.player.dao.IPhoneAndCodeMapDAO;
import com.bottle.api.player.dao.IPlayerDAO;
import com.bottle.api.player.service.interfaces.IPlayerService;
import com.bottle.api.player.vo.PhoneAndCodeMapVO;
import com.bottle.api.player.vo.PlayerVO;

@Service
public class PlayerService implements IPlayerService {
	@Autowired
	private IPhoneAndCodeMapDAO phoneAndCodeMapDAO;
	
	@Autowired
	private IPlayerDAO playerDAO;
	
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
}
