package com.bottle.api.ui.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.api.player.service.interfaces.IPlayerService;
import com.bottle.common.AbstractBaseBean;

@Service
public class UIService extends AbstractBaseBean implements IUIService {
	@Autowired
	private IPlayerService playerService;
	
	@Override
	public void returnMoneyToPlayer(long phoneNumber, double amount) {
		playerService.updateAmount(phoneNumber, amount);
	}
}
