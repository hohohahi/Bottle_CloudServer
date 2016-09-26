package com.bottle.common.redisCache.interfaces;

import java.util.Set;


public interface IRedisClient {
	
	 public  void write(String strKey,String value);
	 
	 public  void write(String strKey,int seconds, String value);
	 
	 public  void write_ByDefaultExpireTime(String strKey,byte[] value);
	 	 
	 public  void write(String strKey,int seconds,byte[] value);
	 
	 public  String read(String strKey);	 
	 
	 public  byte[] readByte(String strKey);
	 
	 public  void remove(String strKey);
	 
	 
	 	
	 
	 
	 
	 
	 // Set operator
	 public void writeIntoSet(String  setKey, String setMember);
	 public Set<String> readSetMembers(String  setKey);
	 public void delSetMember(String  setKey, String setMember);
	 
	 
	 
	 // HashMap operator 
	 
}
