package com.bottle.mina.constants;

import java.text.SimpleDateFormat;

public class MinaConstants {
	public static final int PORT=9123;
	public static final int BUFFER_SIZE=120000;
	public static final String TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	public static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(TIME_FORMAT);
	
	public static final String PRE_LIVE="Pre";
	public static final String LIVE="Live";
	public static final String BETRIX_EXCEPTION_FLAG="betrix exception";
	public static final String BETRIX_BET = "Bet";
	public static final String BETRIX_SETTLE = "Settle";
	public static final String BETRIX_USER = "User";
	public static final int MAX_NUMBER_IN_A_BATCH=5;
	public static final int MAX_SENDING_NUMBER=100;
	public static final int MAX_QUEUE_SIZE=1000000; // 1000000*300=300,000,000 (300M)
	
	public static final int  BOTH_IDLE_TIME=1*60;
	
	public static final String IS_BETRIX_ENABLED="isBetrixEnabled";
	public static final String IS_BETRIX_ENABLED_TRUE="1";

	public static final String _sessionKey_Identifier_= "identifier";
	
	public static final String _jsonKEY_ResponseType_= "messageType";
	
	public enum MinaMessageType {
		_MinaMessage_Type_None(0),
		_MinaMessage_Type_Subscription(1),
		_MinaMessage_Type_TemplateOperaton(2);
		
		long id = 0L;
		MinaMessageType(long id) {
			this.id = id;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
	}
}
