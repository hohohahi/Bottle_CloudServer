package com.shishuo.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.api.common.constants.IWebServiceConstants;
import com.bottle.api.common.exception.MyAPIRuntimeException;
import com.bottle.common.AbstractBaseBean;
import com.shishuo.cms.dao.ITemplateDAO;
import com.shishuo.cms.entity.vo.PositionInfoVO;
import com.shishuo.cms.entity.vo.TemplatePosMapVO;
import com.shishuo.cms.entity.vo.TemplateVO;

@Service
public class TemplateService extends AbstractBaseBean implements ITemplateService {	
	@Autowired
	private ITemplateDAO tempalteDAO;

	@Override
	public List<TemplateVO> selectAll() {
		List<TemplateVO> templateVOList = new ArrayList<TemplateVO>();
		try {
			templateVOList = tempalteDAO.selectAll();
			
			for (TemplateVO templateVO : templateVOList) {
				final long templateId = templateVO.getId();
				final long posNum = templateVO.getPosNum();
				
				List<TemplatePosMapVO> templatePosMapList = tempalteDAO.selectTemplatePosMapByTemplateId(templateId);
				if (null == templatePosMapList) {
					if (0 != posNum) {
						throw new RuntimeException("TemplateService::selectAll: can not get template-pos map data. posNum:" + posNum + "--templatePosMapList:" + templatePosMapList);
					}
				}
				
				final List<PositionInfoVO> positionInfoVOList = new ArrayList<PositionInfoVO>();
				for (TemplatePosMapVO mapVO : templatePosMapList) {
					final PositionInfoVO positionInfoVO = new PositionInfoVO(mapVO.getxPos(), mapVO.getyPos());
					positionInfoVOList.add(positionInfoVO);
				}
				templateVO.setPositionInfoList(positionInfoVOList);
			}
		} catch (Exception e) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_DB_ERROR);
		}
		
		return templateVOList;
	}

	public void validateTemplateWeight(final long weight) {
		if (weight <= 0) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Template_Invalid, "weight is not valid. weight:" + weight);
		}
	}
	
	public void validateTemplateIsMetal(final long isMetal) {
		if (isMetal != 0 && isMetal != 1) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Template_Invalid, "isMetal is not valid. isMetal:" + isMetal);
		}
	}
	
	public void validDuplicatedBarCode(final String barCode) {
		super.validateObject(barCode);
		final TemplateVO insertedTemplate = tempalteDAO.selectTemplateByBarCode(barCode);
		
		if (null != insertedTemplate) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Template_Invalid, "barCode already existed when inserting. barCode:" + barCode);
		}
	}
	
	@Override
	public void insert(TemplateVO vo) {
		super.validateObject(vo);
		
		validateTemplateWeight(vo.getWeight());
		validateTemplateIsMetal(vo.getIsMetal());
		
		//validate posNum
		final long posNum = vo.getPosNum();
		final List<PositionInfoVO> positionList = vo.getPositionInfoList();
		super.validateObject(positionList);
		final int positionListSize = positionList.size();
		
		if (posNum != positionListSize) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Template_Invalid, "posNum is not equal the real size of position list. posNum:" + posNum + "--list size:" + positionListSize);
		}
		
		//validate barCode
		final String barCode = vo.getBarCode();
		if (true == StringUtils.isEmpty(barCode)) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Template_Invalid, "barCode is empty. barCode:" + vo.getBarCode());
		}
		
		if (barCode.length() != 13) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Template_Invalid, "barCode's length is not 13. barCode:" + vo.getBarCode());
		}
		
		validDuplicatedBarCode(barCode);
		
		tempalteDAO.insert(vo);
		final TemplateVO insertedTemplate = tempalteDAO.selectTemplateByBarCode(vo.getBarCode());
		super.validateObject(insertedTemplate);
		
		for (int order = 0; order < positionListSize; order++) {
			final PositionInfoVO pos = positionList.get(order);
			final TemplatePosMapVO mapVO = new TemplatePosMapVO();
			mapVO.setTemplateId(insertedTemplate.getId());
			mapVO.setPosOrder(order);
			mapVO.setxPos(pos.getX());
			mapVO.setyPos(pos.getY());
			
			tempalteDAO.insertTemplatePosMap(mapVO);
		}
	}
}
