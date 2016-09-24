package com.bottle.common.interfaces;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/***
 * 
 * @author Rainman
 * this interface is used to do some extra task in logging, such as showing the function stack.
 */
public interface ILoggerHelper {
	void logging(Logger logger, Level level, Object... messages);
	void logging(Logger logger, Throwable throwable, Level level, Object... messages);
	String getErrorStack(Throwable e);
}
