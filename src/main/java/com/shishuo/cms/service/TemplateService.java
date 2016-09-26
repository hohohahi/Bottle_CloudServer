package com.shishuo.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.api.common.constants.IWebServiceConstants;
import com.bottle.api.common.exception.MyAPIRuntimeException;
import com.bottle.common.AbstractBaseBean;
import com.shishuo.cms.dao.ITemplateDAO;
import com.shishuo.cms.entity.vo.TemplateVO;

@Service
public class TemplateService extends AbstractBaseBean implements ITemplateService {	
	@Autowired
	private ITemplateDAO tempalteDAO;

	@Override
	public List<TemplateVO> selectAll() {
		List<TemplateVO> TemplateVOList = new ArrayList<TemplateVO>();
		try {
			TemplateVOList = tempalteDAO.selectAll();
		} catch (Exception e) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_DB_ERROR);
		}
		
		return TemplateVOList;
	}
}
