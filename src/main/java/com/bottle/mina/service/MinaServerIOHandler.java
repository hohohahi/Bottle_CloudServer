package com.bottle.mina.service;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bottle.api.common.constants.IWebServiceConstants;
import com.bottle.api.common.vo.RestResultVO;
import com.bottle.backoffice.admin.service.AdminService;
import com.bottle.common.AbstractBaseBean;
import com.bottle.mina.constants.MinaConstants;
import com.bottle.mina.vo.AdminLoginVO;
import com.bottle.mina.vo.SubscriptionVO;
import com.shishuo.cms.entity.AdminVO;

@Service
public class MinaServerIOHandler extends AbstractBaseBean implements IoHandler
{   
	@Autowired
	private AdminService adminService;
	
	@Override
    public void exceptionCaught( IoSession session, Throwable cause ) throws Exception
    {
		final String errorMessage = "exception happens, in exceptionCaught function. message:" + cause.getMessage();
		super.logErrorAndStack(cause, errorMessage);
    }
   
    @Override
	public void sessionOpened(IoSession session){    	
    	final String warnMessage = "session opened. id:" + session.getId()
    									+ "--ip address:" +session.getRemoteAddress();
    	System.out.println(warnMessage);
    	super.warnLog(warnMessage);    	
    }
    
    @Override
	public void sessionClosed(IoSession session){
    	final String warnMessage = "session closed. id:" + session.getId()
				+ "--ip address:" +session.getRemoteAddress();
    	super.warnLog(warnMessage);
    	System.out.println(warnMessage);
    }
    
	@Override
    public void messageReceived( IoSession session, Object message ) throws Exception
    {
		try {
			JSONObject jsonObj = JSON.parseObject(message.toString());
			
			final Object responseTypeObj = jsonObj.get(MinaConstants._jsonKEY_ResponseType_);
			if (null == responseTypeObj) {
				throw new NullPointerException("responseTypeObj is null, with key as messageType.");
			}
			
			if (false == (responseTypeObj instanceof Integer)) {
				throw new RuntimeException("responseTypeObj is not instance of Integer.");
			}
			
			Integer responseTypeInt = (Integer)responseTypeObj;
			
			long responseType = responseTypeInt.longValue();
			if (MinaConstants.MinaMessageType._MinaMessage_Type_Subscription.getId() == responseType) {
				SubscriptionVO subscriptionVO = JSONObject.toJavaObject(jsonObj, SubscriptionVO.class);
				
				if (null != session.getAttribute(MinaConstants._sessionKey_Identifier_)) {
					return;
				}
				
				session.setAttribute(MinaConstants._sessionKey_Identifier_, subscriptionVO.getIdentifier());
				super.debugLog("Server receives the subscription message. key:" + subscriptionVO.getIdentifier() + "--session Id:" + session.getId());
				System.out.println("Server receives the subscription message. key:" + subscriptionVO.getIdentifier() + "--session Id:" + session.getId());
			}
			else if (MinaConstants.MinaMessageType._MinaMessage_Type_AdminLogin.getId() == responseType) {
				AdminLoginVO adminLoginVO = JSONObject.toJavaObject(jsonObj, AdminLoginVO.class);
				final AdminVO adminVO = new AdminVO();
				adminVO.setName(adminLoginVO.getUsername());
				adminVO.setPassword(adminLoginVO.getPassword());
				adminService.adminLogin(adminVO);
				
				RestResultVO resultVO = new RestResultVO(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_OK);
				resultVO.setData(adminVO);
				session.write(JSON.toJSONString(resultVO));
			}			
		} catch (Throwable e) {
			super.logErrorAndStack(e, e.getMessage());
		}
    }

	@Override
    public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
    {
		final String debugMessage = "session idle. id:" + session.getId()
				+ "--ip address:" + session.getRemoteAddress()
				+ "--status:" + status.toString();

		super.debugLog(debugMessage);
    }

	@Override
	public void messageSent(IoSession iosession, Object obj) throws Exception {
		final String debugMessage = "message sent. content:" + obj 
										+ "--id:" + iosession.getId()
										+ "--ip address:" + iosession.getRemoteAddress();
		
		super.debugLog(debugMessage);
		System.out.println(debugMessage);
	}


	@Override
	public void sessionCreated(IoSession paramIoSession) throws Exception {		
		final String warnMessage = "session created. id:" + paramIoSession.getId()
										+ "--ip address:" + paramIoSession.getRemoteAddress();
		super.warnLog(warnMessage);
		System.out.println(warnMessage);
	}


	@Override
	public void inputClosed(IoSession paramIoSession) throws Exception {

	}
    
}