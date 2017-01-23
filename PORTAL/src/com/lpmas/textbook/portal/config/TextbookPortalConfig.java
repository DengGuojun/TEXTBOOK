package com.lpmas.textbook.portal.config;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.PropertiesKit;

public class TextbookPortalConfig {

	// 配置文件路径
	public static final String TEXTBOOK_PROP_FILE_NAME = Constants.PROP_FILE_PATH + "/textbook_config";

	// 页面路径
	public final static String PAGE_PATH = Constants.PAGE_PATH + "textbook/";

	public static final int DEFAULT_PAGE_NUM = 1;
	public static final int DEFAULT_PAGE_SIZE = 20;

	public static final String COVER_PATH = PropertiesKit
			.getBundleProperties(TextbookPortalConfig.TEXTBOOK_PROP_FILE_NAME, "COVER_PATH");

	public static final String ROTATION_PATH = PropertiesKit
			.getBundleProperties(TextbookPortalConfig.TEXTBOOK_PROP_FILE_NAME, "ROTATION_PATH");

}
