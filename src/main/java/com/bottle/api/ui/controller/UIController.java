package com.bottle.api.ui.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bottle.api.common.constants.IWebServiceConstants;
import com.bottle.api.common.controller.AbstractBaseController;
import com.bottle.api.common.controller.IController;
import com.bottle.api.common.exception.MyAPIRuntimeException;
import com.bottle.api.common.vo.RestResultVO;
import com.bottle.api.ui.server.IUIService;
import com.bottle.api.ui.vo.UIVO;
import com.shishuo.cms.entity.vo.TemplateVO;

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
	
	@ResponseBody
	@RequestMapping(value="/templatelist", method = RequestMethod.GET)
	protected RestResultVO getTemplateList(final HttpServletResponse response, final HttpServletRequest request){
		RestResultVO resultVO = new RestResultVO(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_OK);
		
		final List<TemplateVO> templateList = uiService.getTemplateList();
		
		resultVO.setData(JSONObject.toJSON(templateList));
		return resultVO;
    }
	
	@ResponseBody
	@RequestMapping(value="/uploadtemplate", method = RequestMethod.POST)
	protected RestResultVO uploadTemplate(final HttpServletResponse response, final HttpServletRequest request, @RequestBody final TemplateVO template){
		RestResultVO resultVO = new RestResultVO(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_OK);
	
		try {
			uiService.uploadTemplate(template);
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
	
	public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
    }
}