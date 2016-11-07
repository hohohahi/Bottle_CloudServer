package com.bottle.api.player.service.interfaces;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.bottle.api.player.vo.PlayerVO;

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
}
