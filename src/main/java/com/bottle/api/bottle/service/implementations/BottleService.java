package com.bottle.api.bottle.service.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.api.bottle.constants.IBottleConstants;
import com.bottle.api.bottle.dao.IBottleDAO;
import com.bottle.api.bottle.service.interfaces.IBottleService;
import com.bottle.api.bottle.vo.BottleVO;
import com.bottle.api.common.constants.IWebServiceConstants;
import com.bottle.api.common.exception.MyAPIRuntimeException;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.redisCache.userSession.ISessionCacheService;

@Service
public class BottleService extends AbstractBaseBean implements IBottleService {	
	@Autowired
	private IBottleDAO bottleDAO;
	
	@Override
	public List<BottleVO> selectAll() {
		List<BottleVO> bottleVOList = new ArrayList<BottleVO>();
		try {
			bottleVOList = bottleDAO.selectAll();
		} catch (Exception e) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_DB_ERROR);
		}
		
		return bottleVOList;
	}

	@Override
	public boolean isBottleExisted_ByIdentifier(String identifier) {
		boolean isExisted = false;
		final BottleVO vo = bottleDAO.selectOneByIdentifier(identifier);
		if (null == vo){
			isExisted = false;
		}
		else {
			isExisted = true;
		}
		
		return isExisted;
	}
}
