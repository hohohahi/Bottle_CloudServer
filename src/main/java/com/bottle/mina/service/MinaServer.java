package com.bottle.mina.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.common.AbstractBaseBean;
import com.bottle.mina.constants.MinaConstants;

@Service
public class MinaServer extends AbstractBaseBean implements IMinaServer {
	@Autowired
	private IoHandler betrixMinaServerHandler;
	
	private IoAcceptor acceptor;
	
	@Override
	public void initialize() {
		super.initialize();
		initSenderThread();
	}
	
	@Override
	public List<IoSession> getActiveSessionList(){
		if (null == acceptor){
			throw new NullPointerException("acceptor is null.");
		}
		
		final List<IoSession> sessionList = new ArrayList<IoSession>();
		
		final Map<Long, IoSession> keyAndSession_Map = acceptor.getManagedSessions();
		if (null == keyAndSession_Map){
			throw new NullPointerException("keyAndSession_Map is null.");
		}
		
		final Set<Entry<Long, IoSession>> entrySet = keyAndSession_Map.entrySet();
		if (null == entrySet){
			throw new NullPointerException("entrySet is null.");
		}
		
		final Iterator<Entry<Long, IoSession>> it = entrySet.iterator();
		if (null == it){
			throw new NullPointerException("it is null.");
		}
		
		while(true == it.hasNext()){
			final Entry<Long, IoSession> entry = it.next();
			if (null == entry){
				throw new NullPointerException("entry is null.");
			}
			
			final IoSession session = entry.getValue();
			if (null == session){
				throw new NullPointerException("session is null.");
			}
			
			sessionList.add(session);
		}
		
		return sessionList;
	}
	
	public void runServer(){
		acceptor = new NioSocketAcceptor(); //AprSocketAcceptor  
		//IoAcceptor acceptor = new AprSocketAcceptor();   
		super.debugLog("BetrixDataSendingMinaServer start");
        //acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
        acceptor.setHandler(betrixMinaServerHandler);
        acceptor.getSessionConfig().setReadBufferSize( MinaConstants.BUFFER_SIZE );
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, MinaConstants.BOTH_IDLE_TIME );
        
        try {
			acceptor.bind( new InetSocketAddress(MinaConstants.PORT) );
		} catch (IOException e) {
			String errorMessage=  "BetrixDataSendingMinaServer banding error";
			super.logErrorAndStack(e, errorMessage);
		}
	}
	
	private void initSenderThread(){
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(new Runnable(){

			@Override
			public void run() {
				runServer();
			}			
		});
	}
}
