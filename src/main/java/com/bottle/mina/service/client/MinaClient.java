package com.bottle.mina.service.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.alibaba.fastjson.JSONObject;
import com.bottle.mina.constants.MinaConstants;
import com.bottle.mina.vo.SubscriptionVO;
import com.bottle.mina.vo.TemplateOperationVO;  
  
public class MinaClient {  
    public static void main(String[] args) {  
        NioSocketConnector connector = new NioSocketConnector();  
        connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
        connector.getSessionConfig().setReadBufferSize( MinaConstants.BUFFER_SIZE );
        connector.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, MinaConstants.BOTH_IDLE_TIME );
        final IoHandler ioHandler = new ClientSessionHandler("is_zhoufeng");
        connector.setHandler(ioHandler);           
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress("127.0.0.1", 9123));  
        connectFuture.awaitUninterruptibly();            
        IoSession session = connectFuture.getSession();     
        
        final SubscriptionVO subscriptionVO = new SubscriptionVO();
        subscriptionVO.setIdentifier("test identifier");
        //session.write(JSONObject.toJSONString(subscriptionVO));
       // session.write(JSONObject.toJSONString(new TemplateOperationVO()));

        session.getCloseFuture().awaitUninterruptibly();
        connector.dispose() ;  
    }  
  
}  