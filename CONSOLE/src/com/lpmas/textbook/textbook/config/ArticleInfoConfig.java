package com.lpmas.textbook.textbook.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;

public class ArticleInfoConfig {

	// 栏目分类
	public final static String POLICY_HIGHLIGHTS = "POLICY_HIGHLIGHTS";
	public final static String ANNOUNCEMENT = "ANNOUNCEMENT";

	public static List<StatusBean<String, String>> SECTION_TYPE_LIST = new ArrayList<StatusBean<String, String>>();
	public static HashMap<String, String> SECTION_TYPE_MAP = new HashMap<String, String>();

	// 状态
	public final static int UNPUBLISHED = 0;
	public final static int PUBLISHED = 1;

	public static List<StatusBean<Integer, String>> PUBLISH_STATUS_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static HashMap<Integer, String> PUBLISH_STATUS_MAP = new HashMap<Integer, String>();

	public static void initSectionTypeList() {
		SECTION_TYPE_LIST = new ArrayList<StatusBean<String, String>>();
		SECTION_TYPE_LIST.add(new StatusBean<String, String>(POLICY_HIGHLIGHTS, "政策要闻"));
		SECTION_TYPE_LIST.add(new StatusBean<String, String>(ANNOUNCEMENT, "通知公告"));
	}

	public static void initSectionTypeMap() {
		SECTION_TYPE_MAP = StatusKit.toMap(SECTION_TYPE_LIST);
	}

	public static void initPublishStatusList() {
		PUBLISH_STATUS_LIST = new ArrayList<StatusBean<Integer, String>>();
		PUBLISH_STATUS_LIST.add(new StatusBean<Integer, String>(UNPUBLISHED, "未发布"));
		PUBLISH_STATUS_LIST.add(new StatusBean<Integer, String>(PUBLISHED, "已发布"));
	}

	public static void initPublishStatusMap() {
		PUBLISH_STATUS_MAP = StatusKit.toMap(PUBLISH_STATUS_LIST);
	}

	static {
		initSectionTypeList();
		initSectionTypeMap();

		initPublishStatusList();
		initPublishStatusMap();
	}
}
