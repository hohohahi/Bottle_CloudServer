package com.bottle.api.ui.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.api.player.service.interfaces.IPlayerService;
import com.bottle.common.AbstractBaseBean;
import com.shishuo.cms.entity.vo.TemplateVO;
import com.shishuo.cms.service.ITemplateService;

@Service
public class UIService extends AbstractBaseBean implements IUIService {
	@Autowired
	private IPlayerService playerService;
	
	@Autowired
	private ITemplateService templateService;
	
	@Override
	public void returnMoneyToPlayer(long phoneNumber, double amount) {
		playerService.updateAmount(phoneNumber, amount);
	}

	@Override
	public List<TemplateVO> getTemplateList() {
		return templateService.selectAll();
	}

	@Override
	public void uploadTemplate(TemplateVO template) {
		templateService.insert(template);
	}
}
