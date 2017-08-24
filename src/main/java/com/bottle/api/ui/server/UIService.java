package com.bottle.api.ui.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.api.bottle.constants.IBottleConstants;
import com.bottle.api.player.service.interfaces.IPlayerService;
import com.bottle.api.player.vo.PlayerVO;
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
	public void returnMoneyToPlayer(final UIVO uiVO) {
		long bottleNum = 0L;
		if (null != uiVO.getCheckRecordList()) {
			bottleNum = uiVO.getCheckRecordList().size();
		}
		else {
			bottleNum = 0L;
		}
		
		final PlayerVO playerVO = new PlayerVO();
		playerVO.setPhoneNumber(uiVO.getPhoneNumber());
		
		playerVO.setAmount(uiVO.getAmount());
		playerVO.setScore(bottleNum);
		
		//the accumulate value, below
		playerVO.setTotalAmount(uiVO.getAmount());
		playerVO.setTotalBottleNum(bottleNum);
		playerVO.setTotalCheckNum(1L);
		
		if (IBottleConstants.CashModeEnum._CacheMode_ReturnMoney_.getId() == uiVO.getCashMode()) {
			playerVO.setTotalReturnMoneyAmount(uiVO.getAmount());
			playerVO.setTotalReturnMoneyCheckNum(1L);
			playerVO.setTotalReturnMoneyBottleNum(bottleNum);
		}
		else if (IBottleConstants.CashModeEnum._CacheMode_Donate_.getId() == uiVO.getCashMode()) {
			playerVO.setTotalDonateAmount(uiVO.getAmount());
			playerVO.setTotalDonateCheckNum(1L);
			playerVO.setTotalDonateBottleNum(bottleNum);
		}
		else {
			throw new RuntimeException("not support cash mode");
		}
		
		//oil 0.08kg, carbon Dioxide 0.025kg
		playerVO.setTotalSavingOilSum(IBottleConstants._OilWeight_PerBottle_*bottleNum);
		playerVO.setTotalSavingCarbonDioxideSum(IBottleConstants._CarbonDioxideWeight_PerBottle_*bottleNum);
		
		playerService.updatePlayer(playerVO);
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
	public void recordCheckResult(final UIVO uiVO) {
		playerService.recordCheckResult(uiVO);		
	}
}
