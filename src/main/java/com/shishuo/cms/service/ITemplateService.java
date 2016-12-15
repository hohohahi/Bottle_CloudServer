package com.shishuo.cms.service;

import java.util.List;

import com.shishuo.cms.entity.vo.TemplateVO;

public interface ITemplateService {
	List<TemplateVO> selectAll();
	void insert(final TemplateVO vo);
	void delete(final TemplateVO vo);
	void fullfilTemplatePosMap(final List<TemplateVO> templateList);
	TemplateVO selectTemplateById(final long templateId);
}
