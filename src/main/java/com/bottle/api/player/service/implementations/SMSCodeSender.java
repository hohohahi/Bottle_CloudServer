package com.bottle.api.player.service.implementations;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.api.common.constants.IWebServiceConstants;
import com.bottle.api.player.service.interfaces.IRandomDigitGenerator;
import com.bottle.api.player.service.interfaces.ISMSCodeSender;

@Service
public class SMSCodeSender implements ISMSCodeSender {
	@Autowired
	private IRandomDigitGenerator verificationCodeGenerator;
	
	@Override
	public String sendRandomSMSCode(final long phoneNum) {
		String randomCode = verificationCodeGenerator.createRandomFourDigitStr();
		
		try {
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod(IWebServiceConstants.SMS_Service_Url_); 
			post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");
			
			
			final String smsContent = IWebServiceConstants._SMS_Content_Prefix_ + randomCode;
			NameValuePair[] data ={ new NameValuePair("Uid", IWebServiceConstants._SMS_Service_Uid_),
									new NameValuePair("Key", IWebServiceConstants.SMS_Service_Key_),
									new NameValuePair("smsMob", phoneNum + ""),
									new NameValuePair("smsText", smsContent)};
			
			post.setRequestBody(data);

			client.executeMethod(post);
			Header[] headers = post.getResponseHeaders();
			int statusCode = post.getStatusCode();
			System.out.println("statusCode:"+statusCode);
			for(Header h : headers)
			{
				System.out.println(h.toString());
			}
			String result = new String(post.getResponseBodyAsString().getBytes("gbk")); 
			System.out.println(result);


			post.releaseConnection();			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return randomCode;
	}
	
	public String assembleSMSContent() {
		String rtnStr = "";
		
		rtnStr = IWebServiceConstants._SMS_Content_Prefix_ + verificationCodeGenerator.createRandomFourDigitStr();
		return rtnStr;
	}
}
