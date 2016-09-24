package com.bottle.api.player.service.implementations;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.bottle.api.player.service.interfaces.IRandomDigitGenerator;

@Service
public class RandomDigitGenerator implements IRandomDigitGenerator {

	@Override
	public String createRandomFourDigitStr() {
		int num1 = new Random().nextInt(10);
		int num2 = new Random().nextInt(10);
		int num3 = new Random().nextInt(10);
		int num4 = new Random().nextInt(10);
		int num5 = new Random().nextInt(10);
		int num6 = new Random().nextInt(10);
		
		String rtnStr = "" + num1 + num2 + num3 + num4 + num5 + num6;
		return rtnStr;
	}
	
	public static void main(String [] args){
		RandomDigitGenerator t1 = new RandomDigitGenerator();
		String str = t1.createRandomFourDigitStr();
		System.out.println(str);
		
		str = t1.createRandomFourDigitStr();
		System.out.println(str);
		
		str = t1.createRandomFourDigitStr();
		System.out.println(str);
		
		str = t1.createRandomFourDigitStr();
		System.out.println(str);
		
		str = t1.createRandomFourDigitStr();
		System.out.println(str);
	}
}
