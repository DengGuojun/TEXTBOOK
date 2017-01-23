package com.lpmas.textbook.console.textbook.business;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class TextbookInfoUtil {

	public static boolean isPositive(String num) {
		Pattern pattern;
		String patt = "^[0-9]+([.]{1}[0-9]+){0,1}$";
		pattern = Pattern.compile(patt);
		return pattern.matcher(num).matches();
	}

	public static String regularPrice(String price) {
		double number = Double.parseDouble(price);
		DecimalFormat df = new DecimalFormat(".00");
		return df.format(number);
	}
}
