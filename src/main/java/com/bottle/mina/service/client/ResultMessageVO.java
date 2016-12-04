package com.bottle.mina.service.client;

import java.io.Serializable;

public class ResultMessageVO implements Serializable{  
    private static final long serialVersionUID = -5859798424107423872L;  
    private String message ;  
  
    public ResultMessageVO(String message) {  
        super();  
        this.message = message;  
    }  
  
    public String getMessage() {  
        return message;  
    }  
  
    public void setMessage(String message) {  
        this.message = message;  
    }  
      
}  
