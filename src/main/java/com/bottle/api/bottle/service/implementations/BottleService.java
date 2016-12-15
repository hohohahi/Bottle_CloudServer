package com.bottle.api.bottle.service.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.api.bottle.dao.IBottleDAO;
import com.bottle.api.bottle.service.interfaces.IBottleService;
import com.bottle.api.bottle.vo.BottleVO;
import com.bottle.api.common.constants.IWebServiceConstants;
import com.bottle.api.common.exception.MyAPIRuntimeException;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.redisCache.userSession.ISessionCacheService;
import com.shishuo.cms.entity.vo.TemplateVO;
import com.shishuo.cms.service.ITemplateService;

@Service
public class BottleService extends AbstractBaseBean implements IBottleService {	
	@Autowired
	private IBottleDAO bottleDAO;
	
	@Autowired
	private ISessionCacheService sessionService;
	
	@Autowired
	private ITemplateService templateService;
	
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

	@Override
	public void removeCacheByPhoneNumber(long phoneNumber) {
		List<BottleVO> voList = bottleDAO.selectAll();
		
		for (final BottleVO vo : voList) {
			super.validateObject(vo);
			final String identifier = vo.getIdentifier();
			
			long cachedPhoneNumber = 0L;
			try {
				cachedPhoneNumber = sessionService.getPhoneNumberByIdentifier(identifier);
				
				if (cachedPhoneNumber == phoneNumber) {
					sessionService.removeByKey(identifier);
				}
			} catch (Exception e) {
				super.debugLog("Error as Debug. message:" + e.getMessage());
			}						
		}		
	}

	@Override
	public List<TemplateVO> selectTemplateListByBottleIdentifier(String identifier) {
		super.validateObject(identifier);
		final BottleVO bottle = bottleDAO.selectOneByIdentifier(identifier);
		super.validateObject(bottle);
		

		List<TemplateVO> rtnTemplateList = bottleDAO.selectTemplateListByBottleId(bottle.getId());
		if (null == rtnTemplateList) {
			rtnTemplateList = new ArrayList<TemplateVO>();
		}
		
		templateService.fullfilTemplatePosMap(rtnTemplateList);
		
		return rtnTemplateList;
	}

	@Override
	public void insertBottleTemplateMap(String identifier, long templateId) {
		super.validateObject(identifier);
		final BottleVO bottle = bottleDAO.selectOneByIdentifier(identifier);
		if (null == bottle) {
			final String errorMessage = "bottle is not existed, identifier:" + identifier;
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Bottle_Not_Existed, errorMessage);
		}

		final TemplateVO template = templateService.selectTemplateById(templateId);
		if (null == template) {
			final String errorMessage = "template is not existed, templateId:" + templateId;
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Template_Not_Existed, errorMessage);
		}
		
		final long bottleId = bottle.getId();
		final TemplateVO templateInMap = bottleDAO.selectOneMapByBottleIdAndTemplateId(bottleId, templateId);
		if (null != templateInMap) {
			final String errorMessage = "bottle-template is already existed, templateId:" + templateId + "--bottleId:" + bottleId;
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_BottleTemplateMap_Already_Existed, errorMessage);
		}
		
		bottleDAO.insertBottleTemplateMap(bottleId, templateId);
	}
}
