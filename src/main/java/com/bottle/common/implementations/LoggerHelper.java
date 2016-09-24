package com.bottle.common.implementations;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bottle.common.interfaces.ILoggerHelper;

@Service
public class LoggerHelper implements ILoggerHelper{
	private static final Logger utilLog = Logger.getLogger("UtilLog");
    private static final String ERROR_MESSAGE_NULL = "message is null.";
    private static final String ERROR_EXCEPTION_NULL = "throwable is null.";
    private static final String ERROR_LOGGER_NULL = "logger is null.";   
    private static final String ERROR_LEVEL_NULL = "level is null."; 

    public void logging(Logger logger, Level level, Object... messages){
    	if (logger.isEnabledFor(level)) {
    		
    		StringBuffer sb = new StringBuffer();
            for (int index = 0; index < messages.length; index++) {
                sb.append(messages[index]);
            }
            
    		logger.log(level, sb.toString());			
        }        
    }
    
    public void logging(Logger logger, Throwable throwable, Level level, Object... messages) {
    	//check, whether logger is null or not.
    	if (null == logger){
    		logInvalidMessage(ERROR_LOGGER_NULL);    		
    		return;
    	}
    	
    	//check, whether level is null or not.
    	if (null == level){
    		logInvalidMessage(ERROR_LEVEL_NULL);    		
    		return;
    	}
    	
    	//check, whether throwable is null or not.
    	if (null == throwable){
    		logInvalidMessage(ERROR_EXCEPTION_NULL);    		
    		return;
    	}    	    	
    	
    	//compose message buf
    	StringBuffer sb = new StringBuffer();    	
    	
    	if (null != messages){
    		for (int index = 0; index < messages.length; index++) {
                sb.append(messages[index]);
            }
    	}    	    	    	    	    	    	    	        
        
        if (null != throwable){
        	String errorStake = getErrorStack(throwable);
            sb.append(". exception stack is ")
              .append(errorStake);
        }        
          
        //show message.
        String msgDetails = sb.toString();
        logMessage(msgDetails, logger, level, throwable);
    }   
    
    public String getErrorStack(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		if(e==null){
			return null;
		}
		
		e.printStackTrace(pw);
		String errorInfo = sw.toString();
		pw.close();
		try {
			sw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return errorInfo;
	}
    
    
    /***
     * log message
     * @param msg
     * @param logger -- logger
     * @param level -- level
     * @param throwable -- throwable exception.
     */
    private void logMessage(String msg, Logger logger, Level level, Throwable throwable){
    	try {
			logger.log(level, msg);
		} catch (Exception e) {
			utilLog.error("error happends", e);
		}    	        
    }
    
    /***
     * log the msg into independent log, when the parameter is invalid.
     * @param msg
     */
    private void logInvalidMessage(String msg){
    	if (null != utilLog){
    		utilLog.error(msg);
    	}
    	else{
    		System.out.println(msg);
    	}
    }
}
