package com.bottle.api.bottle.constants;

public interface IBottleConstants {
	long _CarbonDioxideWeight_PerBottle_ = 25;  //in gram
	long _OilWeight_PerBottle_ = 80;  //in gram
	
	String _donatePhoneNumber_ = "18888888888";
	String _invalidPhoneNumber_ = "19999999999";
	
	enum BottleMountStatusEnum{
		_Not_Mounted_(0),
		_Mounted(1);
		
		long status = 0L;

		BottleMountStatusEnum(long status){
			this.status = status;
		}
		
		public long getStatus() {
			return status;
		}

		public void setStatus(long status) {
			this.status = status;
		}		
	}
	
	enum CashModeEnum{
		_CacheMode_ReturnMoney_(0),
		_CacheMode_Donate_(1);
		
		long id = 0;
		
		CashModeEnum(long id) {
			this.id = id;
		}

		public long getId() {
			return id;
		}
	}
}
