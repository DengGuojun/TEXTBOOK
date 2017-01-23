package com.lpmas.textbook.portal.config;

import com.lpmas.framework.util.PropertiesKit;

public class TextbookDBConfig {

	public static String DB_LINK_TEXTBOOK_W = PropertiesKit
			.getBundleProperties(TextbookPortalConfig.TEXTBOOK_PROP_FILE_NAME, "DB_LINK_TEXTBOOK_W");

	public static String DB_LINK_TEXTBOOK_R = PropertiesKit
			.getBundleProperties(TextbookPortalConfig.TEXTBOOK_PROP_FILE_NAME, "DB_LINK_TEXTBOOK_R");
}
