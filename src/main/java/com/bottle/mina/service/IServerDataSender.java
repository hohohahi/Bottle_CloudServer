package com.bottle.mina.service;

public interface IServerDataSender {
	public abstract void pushDataToQueue(String json);
	public int getToBeSentQueuesize();

}