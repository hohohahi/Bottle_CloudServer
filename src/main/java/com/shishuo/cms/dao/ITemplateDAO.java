package com.shishuo.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.shishuo.cms.entity.vo.TemplatePosMapVO;
import com.shishuo.cms.entity.vo.TemplateVO;

@Repository
public interface ITemplateDAO {
	public void insert(final TemplateVO vo);
	public TemplateVO selectById(@Param("id")final long id);
	public int deleteById(@Param("id")final long id);
	public int deleteTemplatePosMapByTemplateId(@Param("templateId")final long templateId);
	public List<TemplateVO> selectAll();
	public TemplateVO selectTemplateByBarCode(@Param("barCode")final String barCode);
	public void insertTemplatePosMap(final TemplatePosMapVO vo);	
	public List<TemplatePosMapVO> selectTemplatePosMapByTemplateId(@Param("templateId") long templateId);
}
