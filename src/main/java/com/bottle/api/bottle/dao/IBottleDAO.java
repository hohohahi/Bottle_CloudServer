package com.bottle.api.bottle.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bottle.api.bottle.vo.BottleVO;
import com.shishuo.cms.entity.vo.TemplateVO;

@Repository
public interface IBottleDAO {
	public void insert(final BottleVO vo);
	public List<BottleVO> selectAll();
	public BottleVO selectOneByIdentifier(final String identifier);
	public List<TemplateVO> selectTemplateListByBottleId(@Param("bottleId") final long bottleId);
	public void insertBottleTemplateMap(@Param("bottleId")final long bottleId, @Param("templateId")final long templateId);
	public TemplateVO selectOneMapByBottleIdAndTemplateId(@Param("bottleId")final long bottleId, @Param("templateId")final long templateId);
}
