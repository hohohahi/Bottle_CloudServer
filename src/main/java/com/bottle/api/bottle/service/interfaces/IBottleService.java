package com.bottle.api.bottle.service.interfaces;

import java.util.List;

import com.bottle.api.bottle.vo.BottleVO;

public interface IBottleService {
	List<BottleVO> selectAll();
}
