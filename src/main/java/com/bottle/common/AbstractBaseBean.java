package com.bottle.common;

import javax.annotation.PostConstruct;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bottle.common.interfaces.ILoggerHelper;

/***
 * 
 * @author Rainman
 *
 */
public abstract class AbstractBaseBean {
	protected final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	protected ILoggerHelper loggerHelper;
	
	
	private boolean isBusinessInitializationNeeded = false;
	
	@PostConstruct
	public void initialize() {
		final String className = this.getClass().getName();
		String outputMessage = className + " initialized";

		logger.debug(outputMessage);
	}
	
	public void destroy(){
		final String className = this.getClass().getName();
		String outputMessage = className + " destroy";

		System.out.println(outputMessage);
		logger.debug(outputMessage);
	}

	public boolean getIsBusinessInitializationNeeded() {
		return isBusinessInitializationNeeded;
	}

	public void setIsBusinessInitializationNeeded(
			boolean isBusinessInitializationNeeded) {
		this.isBusinessInitializationNeeded = isBusinessInitializationNeeded;
	}
	
	protected void infoLog(String message){
		logger.info(message);
	}
	
	protected void debugLog(String message){
		logger.debug(message);
	}
	
	protected void errorLog(String message){
		logger.error(message);
	}
	
	protected void warnLog(String message){
		logger.warn(message);
	}
	
	protected void logErrorAndStack(final Throwable e, final String errorMessage){
		this.loggerHelper.logging(logger, e, Level.ERROR, errorMessage);
	}
	
	protected void logErrorAndStackByLogger(final Logger inputLogger, final Throwable e, final String errorMessage){
		this.loggerHelper.logging(inputLogger, e, Level.ERROR, errorMessage);
	}
	
	protected void validateObject(final Object object){
		if (null == object){
			throw new NullPointerException("object is null.");
		}
	}
}
