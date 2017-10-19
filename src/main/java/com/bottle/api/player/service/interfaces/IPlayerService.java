package com.bottle.api.player.service.interfaces;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.bottle.api.player.vo.PlayerVO;
import com.bottle.api.ui.vo.UIVO;

public interface IPlayerService {
	void verifySMSCode(final long phoneNumber, final String smsCode);
	void register(final PlayerVO vo);
	void applySMSCode(final long phoneNumber);
	PlayerVO login(final PlayerVO vo);	
	List<PlayerVO> selectAll();	
	void logout(final JSONObject json);
	void mount(final JSONObject json);
	void unmount(final JSONObject json);
	PlayerVO getPlayerInfo_ByPhoneNumber(final long phoneNumber);
	String telWithdraw(final long phoneNumber,double amount,long score);
	void updatePlayer(final PlayerVO playerVO);
	void recordCheckResult(final UIVO uiVO);
}
