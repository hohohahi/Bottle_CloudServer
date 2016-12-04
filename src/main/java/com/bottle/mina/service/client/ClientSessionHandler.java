package com.bottle.mina.service.client;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class ClientSessionHandler implements IoHandler {  
    private final String value  ;  
  
    public ClientSessionHandler(String value){  
        this.value = value ;  
    }  
  
    public void sessionOpened(IoSession session) throws Exception {  
    	final String warnMessage = "session opened. id:" + session.getId()
		+ "--ip address:" +session.getRemoteAddress();
    	System.out.println(warnMessage);
    }  
  
    @Override  
    public void messageReceived(IoSession session, Object message)  
    throws Exception {
    	System.out.println("receive message： message:" + message);
        //ResultMessageVO msg = (ResultMessageVO)message ;  
        //System.out.println("receive message：" + msg.getMessage());   
         
    }  
  
    @Override  
    public void exceptionCaught(IoSession session, Throwable cause)  
    throws Exception {  
    	System.out.println("exception happens.： message:" + cause.getMessage());
        session.closeOnFlush() ;
    }

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("session created.");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("session closed.");
		
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("message sent.message:" + message);
	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		System.out.println("input closed.");
	}  
  
}  
