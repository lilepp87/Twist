package com.tracfone;

import java.io.*;
import java.util.Random;
import javax.script.*;

public class TwistUtils {
	public static final String EMAIL_DOMAIN = "@tracfone.com";
	private static Random random = new Random();
	private static final String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";
	private static final String nameAlphabet = "abcdefghijklmnopqrstuvwxyz";

	public static String createRandomEmail() {
		String localEmailPart = generateAlphanumericString(8);
		String email = localEmailPart + EMAIL_DOMAIN;
		return email;
	}

	private static String generateAlphanumericString(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("Length of string cannot be negative");
		}

		StringBuilder result = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			result.append(alphabet.charAt(random.nextInt(alphabet.length())));
		}
		return result.toString();
	}

	public static String generateName(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("Length of string cannot be negative");
		}

		StringBuilder result = new StringBuilder(length);
		result.append(Character.toUpperCase(nameAlphabet.charAt(random.nextInt(nameAlphabet.length()))));
		for (int i = 1; i < length; i++) {
			result.append(nameAlphabet.charAt(random.nextInt(nameAlphabet.length())));
		}
		return result.toString();
	}

	public static String createRandomUserId() {
		return generateAlphanumericString(10);
	}

	public static void setDelay(int seconds) {
		long value = seconds * 1000L;

		try {
			Thread.sleep(value);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	public static String generateRandomMin() {
		int area = 200 + random.nextInt(789);
		int central = 200 + random.nextInt(799);
		int station = random.nextInt(9999);
		return String.valueOf(area) + String.valueOf(central) + String.format("%04d", station);

		/*long min = 2000000000L;
		long max = 9999999999L;

		return Long.toString(createRandomInteger(min, max));*/
	}
	
	public static String generateCreditCard(String type) {
		String[] card;
		if ("Visa".equalsIgnoreCase(type)) {
			card = RandomCreditCardNumberGenerator.credit_card_number(
					RandomCreditCardNumberGenerator.VISA_PREFIX_LIST, 16, 1);
		} else if ("Discover".equalsIgnoreCase(type)) {
			card = RandomCreditCardNumberGenerator.credit_card_number(
					RandomCreditCardNumberGenerator.DISCOVER_PREFIX_LIST, 16, 1);
		} else if ("Mastercard".equalsIgnoreCase(type)) {
			card = RandomCreditCardNumberGenerator.credit_card_number(
					RandomCreditCardNumberGenerator.MASTERCARD_PREFIX_LIST, 16, 1);
		} else if ("American Express".equalsIgnoreCase(type)) {
			card = RandomCreditCardNumberGenerator.credit_card_number(
					RandomCreditCardNumberGenerator.AMEX_PREFIX_LIST, 15, 1);
		} else {
			throw new IllegalArgumentException("Could not generate Credit card of type: " + type);
		}
				
		return card[0];
	}

	public static String generateACHRoutingNumber() {
		
		String routingNumber = RandomACHGenerator.Routing_number(9);
		return routingNumber;
	}
	
	public static String generateACHAccountNumber() {
		
		//Four digit Account number
		Integer accountNumber = 1000 + (int)(Math.random() * 9999); 
		return accountNumber.toString();
	}
	
	public static long createRandomLong(long min, long max) {
		if (min > max) {
			throw new IllegalArgumentException("Start cannot exceed end.");
		}

		return nextLong(max - min) + min;
	}

	private static long nextLong(long n) {
		if (n <= 0) {
			throw new IllegalArgumentException("n must be positive");
		}

		long bits, val;
		do {
			bits = (random.nextLong() << 1) >>> 1;
			val = bits % n;
		} while (bits - val + (n - 1) < 0L);
		return val;
	}

	public static String generateRandomEsn() {
		long esn =  createRandomLong(90000000000000L, 99999999999999L);
		return Long.toString(esn);
	}
	
	public static String generateRandomSim() {
		long esn =  createRandomLong(8901000000000000000L, 8901999999999999999L);
		return Long.toString(esn);
	}
	//https://lazyzhu.com/imei-generator/
	public static String generate15DigitImei(){
		String imei = null;
		ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine engine = manager.getEngineByName("js");
	    try {
	      FileReader reader = new FileReader("../common-utils/resources/Imei_Generator.js");
	      engine.eval(reader);
	      Invocable inv = (Invocable) engine;
	      imei = (String)inv.invokeFunction("imei_gen", "");
	      reader.close();
	      
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
		
		return imei;
		
	}

}