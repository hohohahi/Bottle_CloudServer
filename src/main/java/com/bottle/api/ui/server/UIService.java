package com.bottle.api.ui.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.api.player.service.interfaces.IPlayerService;
import com.bottle.api.ui.vo.CheckRecordVO;
import com.bottle.api.ui.vo.UIVO;
import com.bottle.backoffice.admin.service.AdminService;
import com.bottle.common.AbstractBaseBean;
import com.shishuo.cms.entity.AdminVO;
import com.shishuo.cms.entity.vo.TemplateVO;
import com.shishuo.cms.service.ITemplateService;

@Service
public class UIService extends AbstractBaseBean implements IUIService {
	@Autowired
	private IPlayerService playerService;
	
	@Autowired
	private ITemplateService templateService;
	
	@Autowired
	private AdminService adminService;

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

	@Override
	public void deleteTemplate(TemplateVO template) {
		templateService.delete(template);
	}

	@Override
	public void adminLogin(AdminVO adminVO) {
		adminService.adminLogin(adminVO);
	}

	@Override
	public void recordCheckResult(long phoneNumber, List<CheckRecordVO> checkResultVOList) {
		playerService.recordCheckResult(phoneNumber, checkResultVOList);		
	}
}
