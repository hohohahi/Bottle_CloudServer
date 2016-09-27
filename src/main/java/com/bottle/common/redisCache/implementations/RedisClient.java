package com.bottle.common.redisCache.implementations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.bottle.common.AbstractBaseBean;
import com.bottle.common.redisCache.common.SerializeUtil;
import com.bottle.common.redisCache.interfaces.IRedisClient;


@Service
public class RedisClient extends AbstractBaseBean implements IRedisClient{
	private ShardedJedis  jedis;
	private String ipAddress = "109.205.92.110";
	private Integer port = 6379;
	private Integer expireTime = 100;
	private Integer checkConnectJobSleepTime=1000*60;

	protected void logErrorAndStack(final Throwable e, final String errorMessage){
		super.logErrorAndStack(e, errorMessage);
	}
	
	protected void debugLog(String message){
		super.debugLog(message);
	}
	
	class RedisConnectCheckJob implements Runnable{

		@Override
		public void run() {
			while(true){						
				try {
					if(isRedisConnectabAvailbe()==false){
						debugLog(" RedisConnectCheckJob  check is fasle.. start connect ");		
						reconnectRedisServer();
					}else{
						debugLog(" RedisConnectCheckJob  check is true ");
					}
					Thread.sleep(checkConnectJobSleepTime);
				} catch (InterruptedException e) {
					logErrorAndStack(e, e.getMessage());
				}
			}
		}
	}
	
	public boolean isRedisConnectabAvailbe(){
		try {
			jedis.set("ConnectionTest", "1");
		} catch (Exception e) {
			logErrorAndStack(e, e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public void initialize(){
		super.initialize();
		super.debugLog("****  ---   start  RedisClient  ---  ****");
		super.debugLog("ip = "+ipAddress+", port = "+port);
		 List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		 shards.add(new JedisShardInfo(ipAddress, port));
		 ShardedJedisPool sjp = new ShardedJedisPool(new JedisPoolConfig(), shards);
		 this.jedis= sjp.getResource();
		 
		 
		 RedisConnectCheckJob checkJob= new RedisConnectCheckJob();
		 Executor executor = Executors.newFixedThreadPool(1);  
		 executor.execute(checkJob);
		 super.debugLog("****  ---   end  RedisClient  ---  ****");
	}
	
	public void reconnectRedisServer(){
		super.debugLog("xxxxxxxxxx  ---   start  reconnect   RedisClient ---  xxxxxxxxxx");
		super.debugLog("ip = "+ipAddress+", port = "+port);
		 List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		 shards.add(new JedisShardInfo(ipAddress, port));
		 ShardedJedisPool sjp = new ShardedJedisPool(new JedisPoolConfig(), shards);
		 this.jedis= sjp.getResource();
		 super.debugLog("xxxxxxxxxx  ---   end  reconnect   RedisClient---    xxxxxxxxxx");
	}
	
	@Override
	public void write(String key,String value) {
		if(key==null){
			throw new RuntimeException("key is null");
		}
		if(value==null){
			throw new RuntimeException("value is null");
		}
		jedis.set(key, value);
	}

	@Override
	public String  read(String keyInfo) {		
		
		if(keyInfo==null){
			throw new RuntimeException("keyInfo  is null");
		}
		
		String jedisInfo=jedis.get(keyInfo);
		return jedisInfo;
	}

	@Override
	public void remove(String strKey) {		
		if(strKey==null){
			throw new RuntimeException("keyInfo  is null");
		}		
		jedis.del(SerializeUtil.serialize(strKey));
	}

	@Override
	public void write_ByDefaultExpireTime(String strKey, byte[] value) {
		jedis.set(SerializeUtil.serialize(strKey), value);
		jedis.expire(SerializeUtil.serialize(strKey), expireTime);
	}

	@Override
	public byte[] readByte(String strKey) {
		return jedis.get(SerializeUtil.serialize(strKey));	
	}

	@Override
	public void writeIntoSet(String setKey, String setMember) {
		jedis.sadd(SerializeUtil.serialize(setKey), SerializeUtil.serialize(setMember));
	}

	@Override
	public Set<String> readSetMembers(String setKey) {
		Set<byte[]>  results= jedis.smembers(SerializeUtil.serialize(setKey));
		Set<String> list=new HashSet<String>();
		for(byte[]  oneResult:results){
			Object  obj=SerializeUtil.unserialize(oneResult);
			if(obj instanceof String){
				list.add((String)obj);
			}
		}
		return list;
	}

	@Override
	public void delSetMember(String setKey, String setMember) {
		jedis.srem(SerializeUtil.serialize(setKey), SerializeUtil.serialize(setMember));
		
	}

	@Override
	public void write(String strKey, int seconds, byte[] value) {
		jedis.set(SerializeUtil.serialize(strKey), value);
		jedis.expire(SerializeUtil.serialize(strKey), seconds);
	}

	@Override
	public void write(String strKey, int seconds, String value) {
		if(strKey==null){
			throw new RuntimeException("key is null");
		}
		if(value==null){
			throw new RuntimeException("value is null");
		}
		jedis.set(strKey, value);
		jedis.expire(strKey, seconds);		
	}
}
