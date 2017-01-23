package com.lpmas.textbook.portal.textbook.business;

public class TextbookIndexUtil {
	public static String getPartIntroduction(String intro) {
		String result = "";
		result = intro.replaceAll("<a>\\s*|\t|\r|\n</a>", "").replaceAll("</?[^>]+>", "");
		;
		int end = result.length();
		result = result.substring(0, end > 60 ? 60 : end);
		if (end > 80) {
			return result + "...";
		} else {
			return result;
		}
	}

	// public static String removeImg(String intro) {
	// StringBuilder result = new StringBuilder();
	// int start;
	// int end;
	// result.append(intro);
	// // 先把img去除
	// while ((start = result.indexOf("<img")) > 0) {
	// end = result.indexOf("/>", start);
	// result = result.delete(start, end + 2);
	// }
	// return result.toString();
	// }

}
