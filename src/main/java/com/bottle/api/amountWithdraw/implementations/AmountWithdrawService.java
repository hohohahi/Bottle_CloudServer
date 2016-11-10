package com.bottle.api.amountWithdraw.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.api.amountWithdraw.FuncUtil;
import com.bottle.api.amountWithdraw.PhoneNunberChargeUtil;
import com.bottle.api.amountWithdraw.WithdrawResponseVO;
import com.bottle.api.amountWithdraw.implement.IAmountWithdrawService;
import com.bottle.api.common.constants.IWebServiceConstants;
import com.bottle.api.common.exception.MyAPIRuntimeException;
import com.bottle.api.player.dao.IPlayerDAO;
import com.bottle.api.player.vo.PlayerVO;


@Service
public class AmountWithdrawService implements IAmountWithdrawService{

	
	@Autowired
	private IPlayerDAO playerDAO;
	
	
	@Override
	public WithdrawResponseVO doWithdraw(double amount, int withdrawType, long phoneNumber) {
		checkValid(phoneNumber,amount);
		if(withdrawType==IAmountWithdrawService.WithdrawByPhoneNumberCharge){
			
			String orderId=getUserfulOrderId();
			try {
				String  returnMsg= PhoneNunberChargeUtil.onlineOrder(String.valueOf(phoneNumber), (int)amount, orderId);
				WithdrawResponseVO vo=new WithdrawResponseVO();
				int errorCode=net.sf.json.JSONObject.fromObject(returnMsg).getInt(PhoneNunberChargeUtil.Return_ErrorCode);
				String reason=net.sf.json.JSONObject.fromObject(returnMsg).getString(PhoneNunberChargeUtil.Return_Reason);
				String result=net.sf.json.JSONObject.fromObject(returnMsg).getString(PhoneNunberChargeUtil.Return_Result);
				if(errorCode==0){
					vo.setOk(true);
					vo.setErrorMsg(reason);
				}if(errorCode==208517){
					vo.setOk(false);
					vo.setErrorMsg("系统账户金额不足");
				}else{
					vo.setOk(false);
					vo.setErrorMsg(reason);
				}
				vo.setOriginReturnMsg(returnMsg);
				vo.setDetails(result);
				
				return vo;
			} catch (Exception e) {
				e.printStackTrace();
				throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Amount_Withdraw_Error, e.getMessage());
			}
		}else{
			return null;
		}
		
	}
	
	
	private String getUserfulOrderId(){
		return FuncUtil.getRandomString();
	}
	private void checkValid(long phoneNumber,double amount){

		final PlayerVO playerVO = playerDAO.selectOne_ByPhoneNumber(phoneNumber);
		if(playerVO==null){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_PhoneNum_Invalid, "用户不存在");
		}
		
		if(playerVO.getAmount()<amount){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Amount_Not_Enough, "用户账户金额不足");
		}
	}

}
