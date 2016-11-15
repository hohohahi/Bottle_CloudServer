package com.bottle.api.ui.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
		
		List<TemplateVO> templateList = new ArrayList<TemplateVO>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp ts = null;
		
		TemplateVO element = new TemplateVO();
		element.setBarCode("1234567890abc");
		element.setId(1L);				
		ts = Timestamp.valueOf(df.format(new Date()));		
		element.setCreatedDate(ts);
		element.setCreatedBy(2L);
		element.setImageCharacteristic("12,34,56,78");
		element.setIsMetal(1L);
		element.setDescription("test");
		element.setName("farmer water");
		element.setModifiedBy(3L);
		element.setModifiedDate(ts);
		element.setWeight(4L);
		templateList.add(element);
		
		TemplateVO element2 = new TemplateVO();
		element2.setBarCode("abcdefghijk01");
		element2.setId(2L);				
		ts = Timestamp.valueOf(df.format(new Date()));		
		element2.setCreatedDate(ts);
		element2.setCreatedBy(2L);
		element2.setImageCharacteristic("87,65,43,21");
		element2.setIsMetal(1L);
		element2.setDescription("test");
		element2.setName("treasure water");
		element2.setModifiedBy(3L);
		element2.setModifiedDate(ts);
		element2.setWeight(4L);
		templateList.add(element2);
		
		resultVO.setData(JSONObject.toJSON(templateList));
		return resultVO;
    }
	
	public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
    }
}