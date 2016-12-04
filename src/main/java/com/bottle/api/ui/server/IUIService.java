package com.bottle.api.ui.server;

import java.util.List;

import com.shishuo.cms.entity.AdminVO;
import com.shishuo.cms.entity.vo.TemplateVO;

public interface IUIService {
	void returnMoneyToPlayer(final long phoneNumber, final double amount);
	List<TemplateVO> getTemplateList();
	void uploadTemplate(final TemplateVO template);
	void deleteTemplate(final TemplateVO template);
	void adminLogin(AdminVO adminVO);
}
