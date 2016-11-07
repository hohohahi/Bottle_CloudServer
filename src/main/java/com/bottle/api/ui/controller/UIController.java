package com.bottle.api.ui.controller;

import java.util.UUID;

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
import com.bottle.api.common.vo.RestResultVO;
import com.bottle.api.ui.server.IUIService;
import com.bottle.api.ui.vo.UIVO;

@Controller
@RequestMapping("/api/ui")
public class UIController extends AbstractBaseController implements IController {
	@Autowired
	private IUIService uiService;
	
	@ResponseBody
	@RequestMapping(value="/ping", method = RequestMethod.POST)
	protected RestResultVO register(final HttpServletResponse response, final HttpServletRequest request, @RequestBody final UIVO vo){
		RestResultVO resultVO = new RestResultVO(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_OK);
		
		return resultVO;
    }
	
	@ResponseBody
	@RequestMapping(value="/money", method = RequestMethod.POST)
	protected RestResultVO returnMoney(final HttpServletResponse response, final HttpServletRequest request, @RequestBody final UIVO vo){
		RestResultVO resultVO = new RestResultVO(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_OK);
		
		uiService.returnMoneyToPlayer(vo.getPhoneNumber(), vo.getAmount());
		return resultVO;
    }
	
	public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
    }
}