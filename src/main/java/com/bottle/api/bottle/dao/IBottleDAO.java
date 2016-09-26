package com.bottle.api.bottle.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bottle.api.bottle.vo.BottleVO;

@Repository
public interface IBottleDAO {
	public void insert(final BottleVO vo);
	public List<BottleVO> selectAll();
}
