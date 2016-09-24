package com.bottle.api.player.dao;

import org.springframework.stereotype.Repository;

import com.bottle.api.player.vo.PlayerVO;

@Repository
public interface IPlayerDAO {
	public void insert(final PlayerVO vo);
	public PlayerVO selectOne_ByPhoneNumber(final long phoneNumber);
}
