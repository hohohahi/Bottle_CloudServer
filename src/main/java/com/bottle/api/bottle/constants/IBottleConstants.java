package com.bottle.api.bottle.constants;

public interface IBottleConstants {
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
}
