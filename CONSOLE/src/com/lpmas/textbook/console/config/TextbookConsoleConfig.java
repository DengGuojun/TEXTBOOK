package com.lpmas.textbook.console.config;

import java.util.List;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.PropertiesKit;

public class TextbookConsoleConfig {

	// 配置文件路径
	public static final String TEXTBOOK_PROP_FILE_NAME = Constants.PROP_FILE_PATH + "/textbook_config";

	// 页面路径
	public final static String PAGE_PATH = Constants.PAGE_PATH + "textbook/";

	public final static int DEFAULT_PAGE_NUM = 1;
	public final static int DEFAULT_PAGE_SIZE = 20;
	// 上传类型
	public static final String UPLOAD_ALLOWED_FILE_TYPE = PropertiesKit.getBundleProperties(TEXTBOOK_PROP_FILE_NAME,
			"UPLOAD_ALLOWED_FILE_TYPE");
	// 封面路径
	public static final String COVER_PATH = PropertiesKit.getBundleProperties(TEXTBOOK_PROP_FILE_NAME, "COVER_PATH");
	// 相对封面路径
	public static final String RELATIVE_COVER_PATH = PropertiesKit.getBundleProperties(TEXTBOOK_PROP_FILE_NAME + "",
			"RELATIVE_COVER_PATH");
	// 物料路径
	public static final String MATERIAL_PATH = PropertiesKit.getBundleProperties(TEXTBOOK_PROP_FILE_NAME,
			"MATERIAL_PATH");
	// 相对物料路径
	public static final String RELATIVE_MATERIAL_PATH = PropertiesKit.getBundleProperties(TEXTBOOK_PROP_FILE_NAME,
			"RELATIVE_MATERIAL_PATH");
	// 轮播图路径
	public static final String ROTATION_PATH = PropertiesKit.getBundleProperties(TEXTBOOK_PROP_FILE_NAME,
			"ROTATION_PATH");
	public static final String RELATIVE_ROTATION_PATH = PropertiesKit.getBundleProperties(TEXTBOOK_PROP_FILE_NAME,
			"RELATIVE_ROTATION_PATH");
	//
	public static final String UPLOAD_TYPE_COVER = "cover";
	//
	public static final String UPLOAD_TYPE_MATERIAL = "material";

	public static final String UPLOAD_TYPE_ROTATION = "rotation";

	// 前台路径
	public static final String PORTAL_SERVER = PropertiesKit.getBundleProperties(TEXTBOOK_PROP_FILE_NAME,
			"PORTAL_SERVER");

	public static List<StatusBean<Integer, String>> MATERIAL_INFO_TYPE_LIST = null;

	static {
		new TextbookConsoleConfig().init();
	}

	private void init() {

	}

}
