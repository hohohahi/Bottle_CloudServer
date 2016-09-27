package com.shishuo.cms.action.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bottle.api.common.vo.UpdateVO;
import com.shishuo.cms.constant.ArticleConstant;
import com.shishuo.cms.exception.FolderNotFoundException;


/**
 * @author 文件action
 * 
 */
@Controller
@RequestMapping("/app")
public class AppAction{

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String list(ArticleConstant.check check,
			HttpServletRequest request, ModelMap modelMap)
			throws FolderNotFoundException {
		
		return "app/main";
	}
	
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public UpdateVO update(HttpServletRequest request, ModelMap modelMap) {
		UpdateVO vo = new UpdateVO();
		return vo;
	}
}
