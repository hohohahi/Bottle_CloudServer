package com.bottle.api.amountWithdraw.implement;

import com.bottle.api.amountWithdraw.WithdrawResponseVO;

public interface IAmountWithdrawService {
	public static final int  WithdrawByPhoneNumberCharge=1;
	public static final int  WithdrawByWeChatAccount=2;
	public static final int  WithdrawByAliPayAccount=3;
	
	public WithdrawResponseVO doWithdraw(double amount,int withdrawType,long phoneNumber);
}
