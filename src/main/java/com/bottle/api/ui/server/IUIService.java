package com.bottle.api.ui.server;

import java.util.List;

import com.bottle.api.ui.vo.UIVO;
import com.shishuo.cms.entity.AdminVO;
import com.shishuo.cms.entity.vo.TemplateVO;

public interface IUIService {
	void returnMoneyToPlayer(final UIVO uiVO);
	List<TemplateVO> getTemplateList();
	void uploadTemplate(final TemplateVO template);
	void deleteTemplate(final TemplateVO template);
	void adminLogin(AdminVO adminVO);
	void recordCheckResult(final UIVO uiVO);
}
