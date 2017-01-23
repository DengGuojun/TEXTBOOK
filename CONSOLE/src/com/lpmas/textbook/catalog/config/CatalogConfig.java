package com.lpmas.textbook.catalog.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;

public class CatalogConfig {
	public static final int ROOT_CATALOG_ID = 0;

	// 页面模版类型，catalog template type
	public final static String CTT_INDEX = "INDEX";// 首页
	public final static String CTT_LIST = "LIST";// 列表页
	public final static String CTT_PRODUCT = "PRODUCT";// 单品页

	public static List<StatusBean<String, String>> TEMPLATE_TYPE_LIST = null;
	public static HashMap<String, String> TEMPLATE_TYPE_MAP = null;

	public final static String CP_CATALOG_CITE = "CATALOG_CITE";// 栏目映射
	public final static String CP_DEFAULT_ORDER_BY = "DEFAULT_ORDER_BY";// 默认排序
	public final static String CP_DEFAULT_PAGE_SIZE = "DEFAULT_PAGE_SIZE";// 默认每页条数

	static {
		new CatalogConfig().init();
	}

	private void init() {
		initTemplateTypeList();
		initTemplateTypeMap();
	}

	private void initTemplateTypeList() {
		TEMPLATE_TYPE_LIST = new ArrayList<StatusBean<String, String>>();
		TEMPLATE_TYPE_LIST.add(new StatusBean<String, String>(CTT_INDEX, "首页"));
		TEMPLATE_TYPE_LIST.add(new StatusBean<String, String>(CTT_LIST, "列表页"));
		TEMPLATE_TYPE_LIST.add(new StatusBean<String, String>(CTT_PRODUCT, "单品页"));
	}

	private void initTemplateTypeMap() {
		TEMPLATE_TYPE_MAP = StatusKit.toMap(TEMPLATE_TYPE_LIST);
	}
}
