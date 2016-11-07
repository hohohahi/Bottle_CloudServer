package com.bottle.api.ui;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class FuncUtil {
	private static final String crf_basci_code="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; 
	public static  String getPasswordMD5(String password) {
        if (password==null || "".equals(password))
            return "";
        try{
            byte[] defaultBytes=password.getBytes("UTF-8");
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i=0;i<messageDigest.length;i++) {
                String hex=Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length()<2)
                    hex="0"+hex;
                hexString.append(hex);
            }
//            String foo = messageDigest.toString();
            return hexString.toString();
        }catch(NoSuchAlgorithmException nsae){
            throw new RuntimeException("Program prblem:",nsae);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Program prblem:",e);
        }
    }
	
	public static String getRandomString(){           
        Random random = new Random();  
        StringBuffer sb = new StringBuffer();  
          
        for(int i = 0 ; i < 18; ++i){  
            int number = random.nextInt(62);//[0,62)
            sb.append(crf_basci_code.charAt(number));  
        }  
        return sb.toString();  
    }  
	

}
