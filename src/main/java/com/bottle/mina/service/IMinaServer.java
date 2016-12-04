package com.bottle.mina.service;

import java.util.List;

import org.apache.mina.core.session.IoSession;

public interface IMinaServer {
	public abstract List<IoSession> getActiveSessionList();
}