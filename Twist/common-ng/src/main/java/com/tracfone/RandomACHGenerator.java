package com.tracfone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

public class RandomACHGenerator {

	public static final List<Integer> prefixArray = new ArrayList<Integer>();
	public static final List<String> routingArray = new ArrayList<String>();
	public static final List<String> accountArray = new ArrayList<String>();
	
	static void addPrefix(int min, int max)
    {
        for(int i = min; i <= max; i++)
        {
        	prefixArray.add(i);
        }
    }
	
	public static void generatePrefixArray(){
		addPrefix(0,12);
		addPrefix(21,32);
		addPrefix(61,72);
		prefixArray.add(80);
	}
	
	static String Routing_number(int length) {

		generatePrefixArray();
		String achNumber = null;
		do{
			achNumber = generateNumber(length);
		}while(!isValidRoutingNumber(achNumber));
		return achNumber;
	}
	
	public static String getRoutingNumber(){
		routingArray.add(0, "121042882");
		routingArray.add(1, "121107882");
		routingArray.add(2, "071923284");
		routingArray.add(3, "122101191");
		String routingNum = routingArray.get(new Random().nextInt(routingArray.size()));
		return routingNum;
	}
	
	public static String getAccountNumber(){
		accountArray.add(0, "4100");
		accountArray.add(1, "4101");
		accountArray.add(2, "4102");
		accountArray.add(3, "4103");
		String accNum = accountArray.get(new Random().nextInt(accountArray.size()));
		return accNum;
	}
	
	

	static String generateNumber(int length){
		String achNumber = prefixArray.get(new Random().nextInt(prefixArray.size())).toString();
		if(achNumber.length()==1){
			achNumber = "0" + achNumber;
		}
		
		// generate digits

		while (achNumber.length() < (length - 1)) {
			achNumber += new Double(Math.floor(Math.random() * 10)).intValue();
		}

		// reverse number and convert to int

		String reversedACHnumberString = strrev(achNumber);

		List<Integer> reversedACHnumberList = new Vector<Integer>();
		for (int i = 0; i < reversedACHnumberString.length(); i++) {
			reversedACHnumberList.add(new Integer(String
					.valueOf(reversedACHnumberString.charAt(i))));
		}

		// calculate sum

		int sum = 0;
		int pos = 0;

		Integer[] reversedACHnumber = reversedACHnumberList
				.toArray(new Integer[reversedACHnumberList.size()]);
		while (pos < length - 1) {

			int odd = reversedACHnumber[pos] * 2;
			if (odd > 9) {
				odd -= 9;
			}

			sum += odd;

			if (pos != (length - 2)) {
				sum += reversedACHnumber[pos + 1];
			}
			pos += 2;
		}

		// calculate check digit

		int checkdigit = new Double(
				((Math.floor(sum / 10) + 1) * 10 - sum) % 10).intValue();
		achNumber += checkdigit;

		return achNumber;

	}
	
	static String strrev(String str) {
		if (str == null)
			return "";
		String revstr = "";
		for (int i = str.length() - 1; i >= 0; i--) {
			revstr += str.charAt(i);
		}

		return revstr;
	}
	
	public static boolean isValidRoutingNumber(String ACHNumber) {
		boolean isValid = false;

		int[] num = new int[ACHNumber.length()];

	    for (int i = 0; i < ACHNumber.length(); i++){
	        num[i] = ACHNumber.charAt(i) - '0';
	    }

	    int sum = 3*(num[0]+ num[3] + num[6]) + 7*(num[1]+num[4]+num[7]) + (num[2] + num[5] + num[8]);
	    
	    if ((sum % 10) == 0) {
			isValid = true;
		}
		return isValid;
	}

	

}