package com.bottle.api.player.service.implementations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bottle.api.amountWithdraw.WithdrawResponseVO;
import com.bottle.api.amountWithdraw.implement.IAmountWithdrawService;
import com.bottle.api.bottle.constants.IBottleConstants;
import com.bottle.api.bottle.dao.IBottleDAO;
import com.bottle.api.bottle.service.interfaces.IBottleService;
import com.bottle.api.bottle.vo.BottleVO;
import com.bottle.api.common.constants.IWebServiceConstants;
import com.bottle.api.common.exception.MyAPIRuntimeException;
import com.bottle.api.player.dao.IPhoneAndCodeMapDAO;
import com.bottle.api.player.dao.IPlayerDAO;
import com.bottle.api.player.service.interfaces.IPlayerService;
import com.bottle.api.player.service.interfaces.ISMSCodeSender;
import com.bottle.api.player.vo.LastCheckRecordStatisticVO;
import com.bottle.api.player.vo.PhoneAndCodeMapVO;
import com.bottle.api.player.vo.PlayerVO;
import com.bottle.api.ui.vo.CheckRecordVO;
import com.bottle.api.ui.vo.PlayerCheckRecordVO;
import com.bottle.api.ui.vo.UIVO;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.redisCache.userSession.ISessionCacheService;
import com.bottle.mina.service.IServerDataSender;

@Service
public class PlayerService extends AbstractBaseBean implements IPlayerService {
	@Autowired
	private IPhoneAndCodeMapDAO phoneAndCodeMapDAO;
	
	@Autowired
	private IPlayerDAO playerDAO;
	
	@Autowired
	private IBottleDAO bottleDAO;
	
	@Autowired
	private ISMSCodeSender smsCodeSender;
	
	@Autowired
	private ISessionCacheService sessionService;
	
	@Autowired
	private IBottleService bottleService;
		
	@Autowired
	private IAmountWithdrawService amountWithdrawService;
	
	@Autowired
	private IServerDataSender serverDataSender;
	
	@Override
	public void verifySMSCode(long phoneNumber, String smsCode) {
		if (false == isMobile(phoneNumber)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_PhoneNum_Invalid);
		}

		final PhoneAndCodeMapVO mapVO = phoneAndCodeMapDAO.selectOne_ByPhoneNumber(phoneNumber);
		if (null == mapVO){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_No_SMSCode_Existed_Under_Phone);
		}
		
		final String actualCode = mapVO.getCode();
		if (false == actualCode.equals(smsCode)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Wrong_SMSCode);
		}
	}
	
	public boolean isMobile(final long phoneNum) {
		return Pattern.matches(IWebServiceConstants.REGEX_MOBILE, phoneNum + "");
	}

	@Override
	public void register(PlayerVO vo) {
		final long phoneNumber = vo.getPhoneNumber();
		final String smsCode = vo.getSmsCode();
		
		final PlayerVO playerVO = playerDAO.selectOne_ByPhoneNumber(phoneNumber);
		if (null != playerVO) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Player_Already_Existed);			
		}
		
		verifySMSCode(phoneNumber, smsCode);
		
		try {
			playerDAO.insert(vo);
			phoneAndCodeMapDAO.deleteByPhoneNum(phoneNumber);
		} catch (Exception e) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_DB_ERROR, e.getMessage());
		}		
	}

	@Override
	public void applySMSCode(final long phoneNumber) {
		super.debugLog("applySMSCode: verify phone number. phoneNumber:" + phoneNumber);
		if (false == isMobile(phoneNumber)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_PhoneNum_Invalid);
		}
		
		super.debugLog("applySMSCode: send sms code");
		String smsCode = "";
		try {
			smsCode = smsCodeSender.sendRandomSMSCode(phoneNumber);
		} catch (Exception e) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_SMSCode_ERROR, e.getMessage());
		}
		super.debugLog("applySMSCode: send sms code over. smsCode:" + smsCode);
		
		try {
			phoneAndCodeMapDAO.deleteByPhoneNum(phoneNumber);		
			final PhoneAndCodeMapVO mapVO = new PhoneAndCodeMapVO();
			mapVO.setPhoneNumber(phoneNumber);
			mapVO.setCode(smsCode);
			phoneAndCodeMapDAO.insert(mapVO);
		} catch (Exception e) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_DB_ERROR, e.getMessage());
		}
		
		super.debugLog("applySMSCode: insert phone and code map over");
	}

	@Override
	public PlayerVO login(PlayerVO vo) {
		final long phoneNumber = vo.getPhoneNumber();
		if (false == isMobile(phoneNumber)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_PhoneNum_Invalid);
		}
		
		final PlayerVO playerVO = playerDAO.selectOne_ByPhoneNumber(phoneNumber);
		if (null == playerVO) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Player_Not_Existed);			
		}
		
		final String password_DB = playerVO.getPassword();
		final String password_Request = vo.getPassword();
		if (false == password_DB.equals(password_Request)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Wrong_Password);
		}
		
		//add to map
		sessionService.setPlayerSession(phoneNumber, playerVO);
		return playerVO;
	}

	@Override
	public PlayerVO getPlayerInfo_ByPhoneNumber(final long phoneNumber) {
		if (false == isMobile(phoneNumber)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_PhoneNum_Invalid);
		}
		
		final PlayerVO playerVO = playerDAO.selectOne_ByPhoneNumber(phoneNumber);
		if (null == playerVO) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Player_Not_Existed);			
		}
		
		final List<PlayerCheckRecordVO> playerCheckRecordList = playerDAO.selectPlayerCheckRecordVOList_ByPhoneNumber_OrderByResultID(phoneNumber);
		for (final PlayerCheckRecordVO subVO : playerCheckRecordList) {
			final List<CheckRecordVO> checkRecordVOList = playerDAO.selectRecordList_ByResultId(subVO.getResultId());
			subVO.setCheckResultVOList(checkRecordVOList);
		}
		
		playerVO.setCheckRecordVOList(playerCheckRecordList);
		playerVO.setLastCheckRecord(getLastStatisticRecordVO_FromLastRecord(playerCheckRecordList));		
		
		return playerVO;
	}
	
	public LastCheckRecordStatisticVO getLastStatisticRecordVO_FromLastRecord(final List<PlayerCheckRecordVO> playerCheckRecordList) {
		super.validateObject(playerCheckRecordList);
		final LastCheckRecordStatisticVO lastCheckRecordStatisticVO = new LastCheckRecordStatisticVO();
		if (playerCheckRecordList.size() > 0) {
			final PlayerCheckRecordVO lastRecord = playerCheckRecordList.get(playerCheckRecordList.size()-1);
			
			lastCheckRecordStatisticVO.setCreatedDate(lastRecord.getCreatedDate());

			final long bottleSum = lastRecord.getCheckResultVOList().size();			
			lastCheckRecordStatisticVO.setSum(bottleSum);

			final Map<String, Long> bottleNameAndSumMap = new HashMap<String, Long>();
			double moneySum = 0.0d;
			for (CheckRecordVO subVO : lastRecord.getCheckResultVOList()) {
				moneySum += subVO.getPrice();

				Long sumUnderTemplateName = bottleNameAndSumMap.get(subVO.getTemplateName());
				if (null == sumUnderTemplateName) {
					sumUnderTemplateName = new Long(1L);
				}
				else {
					sumUnderTemplateName++;
				}

				bottleNameAndSumMap.put(subVO.getTemplateName(), sumUnderTemplateName);				
			}

			lastCheckRecordStatisticVO.setResultMap(bottleNameAndSumMap);
			final BottleVO bottleVO = bottleDAO.selectOneByIdentifier(lastRecord.getMachineIdentifier());
			lastCheckRecordStatisticVO.setLocation(bottleVO.getLocation());
			if (IBottleConstants.CashModeEnum._CacheMode_ReturnMoney_.getId() == lastRecord.getCashMode()) {
				lastCheckRecordStatisticVO.setDonateBottleSum(0L);
				lastCheckRecordStatisticVO.setReturnMoneySum(moneySum);
			}
			else if (IBottleConstants.CashModeEnum._CacheMode_Donate_.getId() == lastRecord.getCashMode()) {				
				lastCheckRecordStatisticVO.setDonateBottleSum(bottleSum);
				lastCheckRecordStatisticVO.setReturnMoneySum(0.0d);				
			}			
		}	
		
		return lastCheckRecordStatisticVO;
	}
	
	@Override
	public List<PlayerVO> selectAll() {
		List<PlayerVO> playerVOList = new ArrayList<PlayerVO>();
		try {
			playerVOList = playerDAO.selectAll();
		} catch (Exception e) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_DB_ERROR);
		}
		
		return playerVOList;
	}

	@Override
	public void mount(JSONObject json) {
		if (null == json){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Parameter_Null, "json is null.");
		}
		
		final Object phoneNumberObj = json.get("phoneNumber");
		long phoneNumber = verifyAndGetLong(phoneNumberObj);
		
		final Object identifierObj = json.get("identifier");
		String identifier = verifyAndGetString(identifierObj);
		
		/*
		final PlayerVO playerVO_Cache = sessionService.getPlayerVOByPhoneNumber(phoneNumber);
		if (false == sessionService.isPlayerLogined(playerVO_Cache, phoneNumber)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Player_Not_Login, json.toString());
		}
		*/
		final boolean isBottledExisted = bottleService.isBottleExisted_ByIdentifier(identifier);
		if (false == isBottledExisted){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Bottle_Not_Existed, json.toString());
		}
		
		/*
		if (true == sessionService.isBottleMounted(identifier)){
			//throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Bottle_Already_Mounted, json.toString());
			sessionService.mount(identifier, phoneNumber);
		}
		else {
			sessionService.mount(identifier, phoneNumber);
		}
		*/		
		super.debugLog("PlayerService::mount: identifier:" + identifier + "--phoneNumber:" + phoneNumber + "--json:" + json);
		serverDataSender.loginMachine(identifier, phoneNumber);
	}
	
	public Long verifyAndGetLong(Object obj){
		if (null == obj){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Parameter_Null, "parameter is null.");
		}
		
		if (false == (obj instanceof Long)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Parameter_Not_Long, "parameter:" + obj);
		}
		
		final Long rtnLong = (Long)obj;
		return rtnLong;		
	}
	
	public Integer verifyAndGetInteger(Object obj){
		if (null == obj){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Parameter_Null, "parameter is null.");
		}
		
		if (false == (obj instanceof Integer)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Parameter_Not_Long, "parameter:" + obj);
		}
		
		final Integer rtnInteger = (Integer)obj;
		return rtnInteger;		
	}
	
	public String verifyAndGetString(Object obj){
		if (null == obj){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Parameter_Null, "parameter is null.");
		}
		
		if (false == (obj instanceof String)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Parameter_Not_String, "parameter:" + obj);
		}
		
		final String rtnString = (String)obj;
		return rtnString;		
	}

	@Override
	public void logout(final JSONObject json) {
		final Object phoneNumberObj = json.get("phoneNumber");
		final long phoneNumber = (long)verifyAndGetLong(phoneNumberObj);
		if (false == isMobile(phoneNumber)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_PhoneNum_Invalid);
		}
		
		final PlayerVO playerVO = playerDAO.selectOne_ByPhoneNumber(phoneNumber);
		if (null == playerVO) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Player_Not_Existed);			
		}
		
		final PlayerVO playerVO_Cache = sessionService.getPlayerVOByPhoneNumber(phoneNumber);
		if (false == sessionService.isPlayerLogined(playerVO_Cache, phoneNumber)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Player_Not_Login, playerVO_Cache.toString());
		}
		
		sessionService.removePlayerSession(phoneNumber);
		bottleService.removeCacheByPhoneNumber(phoneNumber);
	}

	@Override
	public void unmount(JSONObject json) {
		if (null == json){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Parameter_Null, "json is null.");
		}
		
		final Object phoneNumberObj = json.get("phoneNumber");
		long phoneNumber = verifyAndGetLong(phoneNumberObj);
		
		final Object identifierObj = json.get("identifier");
		String identifier = verifyAndGetString(identifierObj);
		
		final PlayerVO playerVO_Cache = sessionService.getPlayerVOByPhoneNumber(phoneNumber);
		if (false == sessionService.isPlayerLogined(playerVO_Cache, phoneNumber)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Player_Not_Login, json.toString());
		}
		
		final boolean isBottledExisted = bottleService.isBottleExisted_ByIdentifier(identifier);
		if (false == isBottledExisted){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Bottle_Not_Existed, json.toString());
		}
		
		if (false == sessionService.isBottleMounted(identifier)){
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Bottle_Not_Mounted, json.toString());
		}
		
		sessionService.unmount(identifier);
	}

	@Override
	public String telWithdraw(long phoneNumber, double amount,long score) {
		WithdrawResponseVO responseVo;
		final PlayerVO playerVO = playerDAO.selectOne_ByPhoneNumber(phoneNumber);
		if(score!=0){
			if(playerVO.getScore()<score){
				throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Amount_Withdraw_Error, "用户积分不够,当前积分:"+playerVO.getScore()+",所需积分："+score);
			}
			double accordingAmountByScorescore  = score/10;
			responseVo=amountWithdrawService.doWithdraw(accordingAmountByScorescore, IAmountWithdrawService.WithdrawByPhoneNumberCharge, phoneNumber);
			if(responseVo.isOk()==false){
				throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Amount_Withdraw_Error, responseVo.getReason());
			}else{
				PlayerVO updatePlayer=new PlayerVO();
				long resultScore=playerVO.getScore()-score;
				updatePlayer.setScore(resultScore);;
				updatePlayer.setPhoneNumber(playerVO.getPhoneNumber());
				playerDAO.updateScoreByPhoneNumber(updatePlayer);
			}
		}else {
			responseVo=amountWithdrawService.doWithdraw(amount, IAmountWithdrawService.WithdrawByPhoneNumberCharge, phoneNumber);
			if(responseVo.isOk()==false){
				throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_Amount_Withdraw_Error, responseVo.getReason());
			}else{				
				PlayerVO vo=new PlayerVO();
				double resultAmount=playerVO.getAmount()-amount;
				vo.setAmount(resultAmount);
				vo.setPhoneNumber(playerVO.getPhoneNumber());
				playerDAO.updateAmountByPhoneNumber(vo);
			}
		}
		return responseVo.getReason();
	}

	@Override
	public void updatePlayer(final PlayerVO srcPlayerVO) {
		final long phoneNumber = srcPlayerVO.getPhoneNumber();
		final PlayerVO playerVO = getPlayerInfo_ByPhoneNumber(phoneNumber);
		super.validateObject(playerVO);
		
		playerVO.setAmount(playerVO.getAmount() + srcPlayerVO.getAmount());  //accumulate
		playerVO.setScore(playerVO.getScore() + srcPlayerVO.getScore());
		
		playerVO.setTotalAmount(playerVO.getTotalAmount() + srcPlayerVO.getTotalAmount());
		playerVO.setTotalCheckNum(playerVO.getTotalCheckNum() + srcPlayerVO.getTotalCheckNum());
		playerVO.setTotalBottleNum(playerVO.getTotalBottleNum() + srcPlayerVO.getTotalBottleNum());
		
		playerVO.setTotalReturnMoneyAmount(playerVO.getTotalReturnMoneyAmount() + srcPlayerVO.getTotalReturnMoneyAmount());
		playerVO.setTotalReturnMoneyCheckNum(playerVO.getTotalReturnMoneyCheckNum() + srcPlayerVO.getTotalReturnMoneyCheckNum());
		playerVO.setTotalReturnMoneyBottleNum(playerVO.getTotalReturnMoneyBottleNum() + srcPlayerVO.getTotalReturnMoneyBottleNum());
		
		playerVO.setTotalDonateAmount(playerVO.getTotalDonateAmount() + srcPlayerVO.getTotalDonateAmount());
		playerVO.setTotalDonateCheckNum(playerVO.getTotalDonateCheckNum() + srcPlayerVO.getTotalDonateCheckNum());
		playerVO.setTotalDonateBottleNum(playerVO.getTotalDonateBottleNum() + srcPlayerVO.getTotalDonateBottleNum());
		
		playerVO.setTotalSavingOilSum(playerVO.getTotalSavingOilSum() + srcPlayerVO.getTotalSavingOilSum());
		playerVO.setTotalSavingCarbonDioxideSum(playerVO.getTotalSavingCarbonDioxideSum() + srcPlayerVO.getTotalSavingCarbonDioxideSum());
		
		playerDAO.updatePlayer(playerVO);
	}

	@Override
	public void recordCheckResult(final UIVO uiVO) {
		final long phoneNumber = uiVO.getPhoneNumber();
		final List<CheckRecordVO> checkResultVOList = uiVO.getCheckRecordList();
		final long cashMode = uiVO.getCashMode();
		final String machineIdentifier = uiVO.getMachineIdentifier();
		
		final PlayerCheckRecordVO playerCheckResultVO = new PlayerCheckRecordVO();
		playerCheckResultVO.setCheckResultVOList(checkResultVOList);
		playerCheckResultVO.setPhoneNumber(phoneNumber);
		playerCheckResultVO.setCashMode(cashMode);
		playerCheckResultVO.setMachineIdentifier(machineIdentifier);
		playerCheckResultVO.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		
		playerDAO.insertPlayerCheckResult(playerCheckResultVO);
		final long resultId = playerDAO.selectMaxResultIdByPhoneNumber(phoneNumber);
		for (CheckRecordVO subVO : checkResultVOList) {
			subVO.setResultId(resultId);
			playerDAO.insertRecordMap(subVO);
		}
	}
}
