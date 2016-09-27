package com.shishuo.cms.action.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bottle.api.common.vo.UpdateVO;
import com.bottle.api.player.vo.PlayerVO;
import com.bottle.common.redisCache.userSession.ISessionCacheService;
import com.shishuo.cms.constant.ArticleConstant;
import com.shishuo.cms.exception.FolderNotFoundException;


/**
 * @author 文件action
 * 
 */
@Controller
@RequestMapping("/app")
public class AppAction{
	@Autowired
	private ISessionCacheService sessionService;
	
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
		vo.setBottleStatus(1);
		
		final String identifier = "40ec2351-af21-4d1c-9a92-85629f43a0bc";
		boolean isMounted = sessionService.isBottleMounted(identifier);
		if (isMounted == false){
			vo.setMountStatus(0);
		}
		else {
			vo.setMountStatus(1);
			final long phoneNumber = sessionService.getPhoneNumberByIdentifier(identifier);
			final PlayerVO playerVO = sessionService.getPlayerVOByPhoneNumber(phoneNumber);
			vo.setPhoneNumber(phoneNumber);
			vo.setAmount(playerVO.getAmount());
		}
		
		return vo;
	}
}
