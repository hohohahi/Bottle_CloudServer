package com.bottle.api.common.constants;

public interface IWebServiceConstants {
	final String _SMS_Content_Prefix_ = "您的注册验证码是";
	final String _SMS_Service_Uid_ = "hohohahi";
	final String SMS_Service_Key_ = "444e0eb7e1841e7d062e";
	final String SMS_Service_Url_ = "http://gbk.sms.webchinese.cn";	

	 final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	enum enumWebServiceErrorCOde{
		_errorCode_OK(0L, "ok");
		
		long id;
		String message = "";
		
		enumWebServiceErrorCOde(final long id, final String message){
			this.id = id;
			this.message = message;
		}
		
		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}				
	}
	
	enum RestServiceExceptionEnum{
		_RestService_Exception_OK(0L, "ok"),
		_RestService_Exception_UNKNOWN(1L, "unknown error"),
		_RestService_Exception_PhoneNum_Invalid(2L, "phone number is invalid"),
		_RestService_Exception_No_SMSCode_Existed_Under_Phone(3L, "There is no smsCode existed, under this phoneNumber"),
		_RestService_Exception_Wrong_SMSCode(4L, "The wrong SMS code"),
		_RestService_Exception_DB_ERROR(5L, "Database operation error"),
		_RestService_Exception_SMSCode_ERROR(6L, "Send SMS code error"),
		_RestService_Exception_Player_Not_Existed(7L, "The player is not existed."),
		_RestService_Exception_Player_Already_Existed(8L, "The player is already existed"),
		_RestService_Exception_Wrong_Password(9L, "The password is wrong"),
		_RestService_Exception_Player_Already_Login(10L, "The player is already login"),
		_RestService_Exception_Parameter_Null(11L, "The parameter is null"),
		_RestService_Exception_Parameter_Not_Long(12L, "The parameter is not instance of Long"),
		_RestService_Exception_Parameter_Not_String(13L, "The parameter is not instance of String"),
		_RestService_Exception_Bottle_Not_Existed(14L, "The bottle is not existed"),
		_RestService_Exception_Player_Not_Login(15L, "The player is not login"),
		_RestService_Exception_Bottle_Already_Mounted(16L, "The bottle is already mounted"),
		_RestService_Exception_Bottle_Not_Mounted(17L, "The bottle is not mounted"),
		_RestService_Exception_Amount_Not_Enough(18L, "user amount is not enought"),
		_RestService_Exception_Amount_Withdraw_Error(19L, "amount withdraw error"),
		_RestService_Exception_Template_Invalid(20L, "Template is invalid format"),
		_RestService_Exception_AdminLogin_UserNotExisted(21L, "admin user is not existed."),
		_RestService_Exception_AdminLogin_WrongPassword(22L, "admin user's password is wrong."),
		_RestService_Exception_Template_Not_Existed(23L, "template is not existed."),
		_RestService_Exception_BottleTemplateMap_Already_Existed(24L, "bottle-template map is already existed."),
		_RestService_Exception_BottleTemplateMap_Not_Existed(25L, "bottle-template map is already existed.");
		
		private long errorCode = 0L;
		private String errorMessage = "";
		private String extraMessage = "";
		
		RestServiceExceptionEnum(final long errorCode, final String errorMessage){
			this.errorCode = errorCode;
			this.errorMessage = errorMessage;
		}
		
		public long getErrorCode() {
			return errorCode;
		}
		public void setErrorCode(long errorCode) {
			this.errorCode = errorCode;
		}
		public String getErrorMessage() {
			return errorMessage;
		}
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public String getExtraMessage() {
			return extraMessage;
		}

		public void setExtraMessage(String extraMessage) {
			this.extraMessage = extraMessage;
		}
	}
	
	enum RestParameter_Service_Enum{
		_RestParameter_Service_Invalid(0),
		_RestParameter_Service_MyLastBets(1);
		
		private long id = 0L;

		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		
		RestParameter_Service_Enum(final long id){
			this.id = id;
		}
	}
	
	enum RestParameter_Action_Enum{
		_RestParameter_Action_Subscription(1),
		_RestParameter_Action_UnSubscription(2);
		
		private long id = 0L;

		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		
		RestParameter_Action_Enum(final long id){
			this.id = id;
		}
	}
}
