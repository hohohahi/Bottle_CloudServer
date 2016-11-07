package com.bottle.api.player.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bottle.api.player.vo.PhoneAndCodeMapVO;

@Repository
public interface IPhoneAndCodeMapDAO {
	public void insert(PhoneAndCodeMapVO mapVO);
	public void deleteByPhoneNum(@Param("phoneNumber") final long phoneNumber);
	public int selectCount_ByPhoneNumber(@Param("phoneNumber") final long phoneNumber);
	public PhoneAndCodeMapVO selectOne_ByPhoneNumber(@Param("phoneNumber") final long phoneNumber);
}
