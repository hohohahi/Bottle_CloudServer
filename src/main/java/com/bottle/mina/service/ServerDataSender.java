package com.bottle.mina.service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bottle.common.AbstractBaseBean;
import com.bottle.mina.constants.MinaConstants;
import com.bottle.mina.vo.ServerMessageVO;

@Service
public class ServerDataSender extends AbstractBaseBean implements IServerDataSender{
	private BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

	@Autowired
	private IMinaServer betrixServer;
	
	@Override
	public void initialize(){
		this.initSenderThread();
		super.initialize();
	}
	
	private void initSenderThread(){
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(new Runnable(){

			@Override
			public void run() {
		    	boolean runFlag = true;
				while(true == runFlag){
		    		String jsonStr = "";
		    		int leftQueueSize = 0;
					try {
						jsonStr = queue.take();
						leftQueueSize = queue.size();
						final List<IoSession> sessionList = betrixServer.getActiveSessionList();
			    		
			    		for (final IoSession session : sessionList){
			    			pushMessageToClients(jsonStr, session);
			    		}		 			    		
					} catch (Exception e) {
						final String errorMessage = "exception happens. message:" + e.getMessage();
						logErrorAndStack(e, errorMessage);
					} finally {
						final String message = "send a message to clients. leftQueueSize:" + leftQueueSize
													+ "-- content:" + jsonStr;
						debugMessage(message);
					}
		    	}
			}			
		});
	}
	
	public void logErrorAndStack(final Throwable e, final String errorMessage){
		super.logErrorAndStack(e, errorMessage);
	}
	
	public void debugMessage(final String message){
		super.debugLog(message);
	}
	private void pushMessageToClients(final String message, final IoSession session){		
		final long startTime = System.currentTimeMillis();
		final StringBuilder buf = new StringBuilder();
		buf.append("push message to client. ");
		
		try {			
			if (true == StringUtils.isEmpty(message)){
				throw new RuntimeException("message is null or empty. message:" + message);
			}
			
			if (null == session){
				throw new NullPointerException("session is null.");
			}
			
			if(!session.isConnected()){ // we don't need to send heartheat package to client due to this function --need to double confirm it in real environment. 
				 session.close();
				 final String errorMessage = "session is not connected already. try to close it.";
				 throw new RuntimeException(errorMessage);
			}
						
			session.write(message);
			final long spentTime = System.currentTimeMillis() - startTime;
			buf.append("push message to client. id:" + session.getId()
							+ "--spentTime:" + spentTime);
		} catch (Exception e) {
			final String errorMessage = "exception happens. catch this error, and let other session goes on. message:" + e.getMessage();
			super.logErrorAndStack(e, errorMessage);
			buf.append(errorMessage);
		} finally{
			super.debugLog(buf.toString());
		}
		
	}
	
	@Override
	public void pushDataToQueue(String json){
		queue.offer(json);
	}

	@Override
	public int getToBeSentQueuesize(){
		return queue.size();
	}

	@Override
	public void loginMachine(String identifier, long phoneNumber) {
		final List<IoSession> sessionList = betrixServer.getActiveSessionList();
		super.debugLog("loginMachine: session list size:" + sessionList.size());
		for (final IoSession session : sessionList){
			final Object valueObj = session.getAttribute(MinaConstants._sessionKey_Identifier_);
			if (false == (valueObj instanceof String)) {
				//throw new RuntimeException("valueObject is not instance of String. valueObj:" + valueObj + "--session Id:" + session.getId());
				final String errorMessage = "valueObject is not instance of String. valueObj:" + valueObj + "--session Id:" + session.getId();
				System.out.println(errorMessage);
				super.errorLog(errorMessage);
				continue;
			}
			
			final String dstClientIdentifier = (String)valueObj;
			
			if (true == identifier.equals(dstClientIdentifier)) {
				ServerMessageVO message = new ServerMessageVO();
				message.setMessageType( MinaConstants.MinaMessageType._MinaMessage_Type_FromServer.getId());
				message.setSubMessageType( MinaConstants.MinaMessageType._MinaMessage_Type_FromServer_LoginMachine.getId());
				message.setPhoneNumber(phoneNumber);
				message.setIdentifier(identifier);				
				
				pushMessageToClients(JSONObject.toJSON(message).toString(), session);
			}						
		}		
	}
	
	public static void main(String [] args) {
		ServerMessageVO message = new ServerMessageVO();
		message.setMessageType( MinaConstants.MinaMessageType._MinaMessage_Type_FromServer.getId());
		message.setSubMessageType( MinaConstants.MinaMessageType._MinaMessage_Type_FromServer_LoginMachine.getId());
		message.setPhoneNumber(18975811415L);
		message.setIdentifier("testabc123");
		
		System.out.println(JSONObject.toJSON(message).toString());
	}
	
}
