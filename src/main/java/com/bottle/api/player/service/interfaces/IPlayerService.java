package com.bottle.api.player.service.interfaces;

import java.util.List;

import com.bottle.api.player.vo.PlayerVO;

public interface IPlayerService {
	void verifySMSCode(final long phoneNumber, final String smsCode);
	void register(final PlayerVO vo);
	void applySMSCode(final long phoneNumber);
	PlayerVO login(final PlayerVO vo);
	List<PlayerVO> selectAll();
}
