package com.bottle.api.player.controller;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bottle.api.common.constants.IWebServiceConstants;
import com.bottle.api.common.controller.AbstractBaseController;
import com.bottle.api.common.controller.IController;
import com.bottle.api.common.exception.MyAPIRuntimeException;
import com.bottle.api.common.vo.RestResultVO;
import com.bottle.api.player.dao.IPhoneAndCodeMapDAO;
import com.bottle.api.player.service.interfaces.IPlayerService;
import com.bottle.api.player.vo.PlayerVO;
import com.shishuo.cms.dao.AdminDao;

@Controller
@RequestMapping("/api/player")
public class PlayerController extends AbstractBaseController implements IController {
	@Autowired
	private IPhoneAndCodeMapDAO phoneAndCodeMapDAO;
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private IPlayerService service;
	
	@ResponseBody
	@RequestMapping(value="/registeration", method = RequestMethod.POST)
	protected RestResultVO register(final HttpServletResponse response, final HttpServletRequest request, @RequestBody final PlayerVO vo){
		RestResultVO resultVO = new RestResultVO(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_OK);
		
		try {
			service.register(vo);
		} catch (Exception e) {
			if (true == (e instanceof MyAPIRuntimeException)){
				MyAPIRuntimeException myException = (MyAPIRuntimeException)e;
				resultVO.assignExceptionEnum(myException.getErrorDefinitionEnum());
			}
			else {
				resultVO.assignExceptionEnum(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_UNKNOWN);
			}
			
			super.logErrorAndStack(e, e.getMessage());
		}
		
		return resultVO;
    }
	
	@ResponseBody
	@RequestMapping(value="/login", method = RequestMethod.POST)
	protected RestResultVO login(final HttpServletResponse response, final HttpServletRequest request, @RequestBody final PlayerVO vo){
		RestResultVO resultVO = new RestResultVO(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_OK);
		
		try {
			final PlayerVO realVO = service.login(vo);
			resultVO.setData(realVO);
		} catch (Exception e) {
			if (true == (e instanceof MyAPIRuntimeException)){
				MyAPIRuntimeException myException = (MyAPIRuntimeException)e;
				resultVO.assignExceptionEnum(myException.getErrorDefinitionEnum());
			}
			else {
				resultVO.assignExceptionEnum(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_UNKNOWN);
			}
			
			super.logErrorAndStack(e, e.getMessage());
		}
		
		return resultVO;
    }
	
	@ResponseBody
	@RequestMapping(value="/smscode/application", method = RequestMethod.POST)
	protected RestResultVO lySMSCode(final HttpServletResponse response, final HttpServletRequest request, @RequestBody final PlayerVO vo){
		RestResultVO resultVO = new RestResultVO(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_OK);
		
		final long phoneNumber = vo.getPhoneNumber();
		try {
			service.applySMSCode(phoneNumber);
		} catch (Exception e) {
			if (true == (e instanceof MyAPIRuntimeException)){
				MyAPIRuntimeException myException = (MyAPIRuntimeException)e;
				resultVO.assignExceptionEnum(myException.getErrorDefinitionEnum());
			}
			else {
				resultVO.assignExceptionEnum(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_UNKNOWN);
			}
			
			super.logErrorAndStack(e, e.getMessage());
		}
		
		return resultVO;
    }
/*	
	@ResponseBody
	@RequestMapping(value="/smscode/execution", method = RequestMethod.POST)
	protected RestResultVO verifySMSCode(final HttpServletResponse response, final HttpServletRequest request, @RequestBody final VerificationVO vo){
		RestResultVO resultVO = new RestResultVO(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_OK);
		
		final long phoneNumber = vo.getPhoneNumber();
		final String smsCode = vo.getCode();
		
		try {
			service.verifySMSCode(phoneNumber, smsCode);
		} catch (Exception e) {
			if (true == (e instanceof MyAPIRuntimeException)){
				MyAPIRuntimeException myException = (MyAPIRuntimeException)e;
				resultVO.assignExceptionEnum(myException.getErrorDefinitionEnum());
			}
			else {
				resultVO.assignExceptionEnum(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_UNKNOWN);
			}
		}
		
		return resultVO;
    }
*/
	public boolean isMobile(final long phoneNum) {
		return Pattern.matches(IWebServiceConstants.REGEX_MOBILE, phoneNum + "");
	}
}
