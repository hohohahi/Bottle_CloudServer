package com.shishuo.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.shishuo.cms.entity.vo.TemplateVO;

@Repository
public interface ITemplateDAO {
	public void insert(final TemplateVO vo);
	public List<TemplateVO> selectAll();
}
