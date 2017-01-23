package com.lpmas.textbook.textbook.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;

public class TextbookInfoConfig {
	public final static String TXT_DESC_PRICE = "PRICE";
	public final static String TXT_DESC_OVERALL_CLASSIFICATION = "OVERALL_CLASSIFICATION";
	public final static String TXT_DESC_YEAR = "YEAR";
	public final static String TXT_DESC_PROVINCE = "PROVINCE";
	public final static String TXT_DESC_TEXTBOOK_CLASS = "TEXTBOOK_CLASS";
	public final static String TXT_DESC_TEXTBOOK_TYPE = "TEXTBOOK_TYPE";
	public final static String TXT_DESC_GROUP_EDIT = "GROUP_EDIT";
	public final static String TXT_DESC_MAIN_EDIT = "MAIN_EDIT";
	public final static String TXT_DESC_GUIDE_TEACHER = "GUIDE_TEACHER";
	public final static String TXT_DESC_PRESS = "PRESS";
	public final static String TXT_DESC_PUBLICATION_DATE = "PUBLICATION_DATE";
	public final static String TXT_DESC_BOOK_FORMAT = "BOOK_FORMAT";
	public final static String TXT_DESC_INTRODUCTION = "INTRODUCTION";
	public final static String TXT_DESC_WRITE_DESCRIPTION = "WRITE_DESCRIPTION";
	public final static String TXT_DESC_CONTENTS = "CONTENTS";
	public final static String TXT_DESC_FIRST_CHAPTER = "FIRST_CHAPTER";
	public final static String TXT_DESC_CATALOGID = "CATALOGID";

	// 总体分类s
	public final static String TXT_CLASSIFICATION_PUBLIC_BASE = "PUBLIC_BASE";
	public final static String TXT_CLASSIFICATION_PROFESSION_ATTAINMENT = "PROFESSION_ATTAINMENT";

	// 封面
	public static final String COVER_1 = "COVER_1";
	public static final String COVER_2 = "COVER_2";
	public static final String COVER_3 = "COVER_3";
	public static final String COVER_4 = "COVER_4";
	public static final String COVER_5 = "COVER_5";
	public static final String COVER_6 = "COVER_6";
	public static final String COVER_7 = "COVER_7";
	public static final String COVER_8 = "COVER_8";
	public static final String COVER_9 = "COVER_9";
	public static final String COVER_0 = "COVER_0";

	// 图书分类
	public static final String TXT_CLASS_AGRICULTURAL_ENGINEERING = "AGRICULTURAL_ENGINEERING"; // 农业工程
	public static final String TXT_CLASS_AQUACULTURE = "AQUACULTURE";// 养殖业
	public static final String TXT_CLASS_CROP_FARMING = "CROP_FARMING";// 种植业
	public static final String TXT_CLASS_PUBLIC_ADMINISTRATION = "PUBLIC_ADMINISTRATION";// 公共基础
	public static final String TXT_CLASS_MANAGEMENT = "MANAGEMENT";// 经营管理
	public static final String TXT_CLASS_OTHER = "OTHER";// 少数民族语言类

	// 图书类型
	public static final String TXT_TYPE_NEW_TYPE_FARMER_CULTIVATE = "NEW_TYPE_FARMER_CULTIVATE";
	public static final String TXT_TYPE_SECONDARY_VOCATIONAL_EDUCATION = "SECONDARY_VOCATIONAL_EDUCATION";

	public final static String SELLING_STATUS_OFF = "SELLING_OFF";
	public final static String SELLING_STATUS_ON = "SELLING_ON";

	public static List<StatusBean<String, String>> TXT_DESC_LIST = new ArrayList<StatusBean<String, String>>();
	public static HashMap<String, String> TXT_DESC_MAP = new HashMap<String, String>();

	public static List<StatusBean<String, String>> TXT_CLASSIFICATION_LIST = new ArrayList<StatusBean<String, String>>();
	public static HashMap<String, String> TXT_CLASSIFICATION_MAP = new HashMap<String, String>();

	public static List<StatusBean<String, String>> TXT_COVER_LIST = new ArrayList<StatusBean<String, String>>();
	public static HashMap<String, String> TXT_COVER_MAP = new HashMap<String, String>();

	public static List<StatusBean<String, String>> TXT_CLASS_LIST = new ArrayList<StatusBean<String, String>>();
	public static HashMap<String, String> TXT_CLASS_MAP = new HashMap<String, String>();

	public static List<StatusBean<String, String>> TXT_TYPE_LIST = new ArrayList<StatusBean<String, String>>();
	public static HashMap<String, String> TXT_TYPE_MAP = new HashMap<String, String>();

	public static List<StatusBean<String, String>> SELLING_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
	public static HashMap<String, String> SELLING_STATUS_MAP = new HashMap<String, String>();

	public static void initSellingStatusList() {
		SELLING_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
		SELLING_STATUS_LIST.add(new StatusBean<String, String>(SELLING_STATUS_OFF, "下架"));
		SELLING_STATUS_LIST.add(new StatusBean<String, String>(SELLING_STATUS_ON, "上架"));
	}

	public static void initSellingStatusMap() {
		SELLING_STATUS_MAP = StatusKit.toMap(SELLING_STATUS_LIST);
	}

	public static void initTypeList() {
		TXT_TYPE_LIST = new ArrayList<StatusBean<String, String>>();
		TXT_TYPE_LIST.add(new StatusBean<String, String>(TXT_TYPE_NEW_TYPE_FARMER_CULTIVATE, "新型职业农民培育"));
		TXT_TYPE_LIST.add(new StatusBean<String, String>(TXT_TYPE_SECONDARY_VOCATIONAL_EDUCATION, "中职教育"));
	}

	public static void initTypeMap() {
		TXT_TYPE_MAP = StatusKit.toMap(TXT_TYPE_LIST);
	}

	public static void initClassList() {
		TXT_CLASS_LIST = new ArrayList<StatusBean<String, String>>();
		TXT_CLASS_LIST.add(new StatusBean<String, String>(TXT_CLASS_CROP_FARMING, "种植业"));
		TXT_CLASS_LIST.add(new StatusBean<String, String>(TXT_CLASS_AQUACULTURE, "养殖业"));
		TXT_CLASS_LIST.add(new StatusBean<String, String>(TXT_CLASS_PUBLIC_ADMINISTRATION, "公共基础"));
		TXT_CLASS_LIST.add(new StatusBean<String, String>(TXT_CLASS_AGRICULTURAL_ENGINEERING, "农业工程"));
		TXT_CLASS_LIST.add(new StatusBean<String, String>(TXT_CLASS_MANAGEMENT, "经营管理"));
		TXT_CLASS_LIST.add(new StatusBean<String, String>(TXT_CLASS_OTHER, "少数民族语言类"));
	}

	public static void initClassMap() {
		TXT_CLASS_MAP = StatusKit.toMap(TXT_CLASS_LIST);
	}

	public static void initTextbookList() {
		TXT_DESC_LIST = new ArrayList<StatusBean<String, String>>();
		TXT_DESC_LIST.add(new StatusBean<String, String>(TXT_DESC_PRICE, "价格"));
		TXT_DESC_LIST.add(new StatusBean<String, String>(TXT_DESC_OVERALL_CLASSIFICATION, "总体分类"));
		TXT_DESC_LIST.add(new StatusBean<String, String>(TXT_DESC_YEAR, "年份"));
		TXT_DESC_LIST.add(new StatusBean<String, String>(TXT_DESC_PROVINCE, "省份"));
		TXT_DESC_LIST.add(new StatusBean<String, String>(TXT_DESC_TEXTBOOK_CLASS, "图书分类"));
		TXT_DESC_LIST.add(new StatusBean<String, String>(TXT_DESC_TEXTBOOK_TYPE, "图书类型"));
		TXT_DESC_LIST.add(new StatusBean<String, String>(TXT_DESC_GROUP_EDIT, "组编"));
		TXT_DESC_LIST.add(new StatusBean<String, String>(TXT_DESC_MAIN_EDIT, "主编"));
		TXT_DESC_LIST.add(new StatusBean<String, String>(TXT_DESC_GUIDE_TEACHER, "指导老师"));
		TXT_DESC_LIST.add(new StatusBean<String, String>(TXT_DESC_PRESS, "出版社"));
		TXT_DESC_LIST.add(new StatusBean<String, String>(TXT_DESC_PUBLICATION_DATE, "出版日期"));
		TXT_DESC_LIST.add(new StatusBean<String, String>(TXT_DESC_BOOK_FORMAT, "开本"));
		TXT_DESC_LIST.add(new StatusBean<String, String>(TXT_DESC_INTRODUCTION, "内容介绍"));
		TXT_DESC_LIST.add(new StatusBean<String, String>(TXT_DESC_WRITE_DESCRIPTION, "编写说明"));
		TXT_DESC_LIST.add(new StatusBean<String, String>(TXT_DESC_CONTENTS, "目录"));
		TXT_DESC_LIST.add(new StatusBean<String, String>(TXT_DESC_FIRST_CHAPTER, "第一章内容"));
		TXT_DESC_LIST.add(new StatusBean<String, String>(TXT_DESC_CATALOGID, "专业"));

	}

	public static void initTextbookMap() {
		TXT_DESC_MAP = StatusKit.toMap(TXT_DESC_LIST);

	}

	public static void initClassificationList() {
		TXT_CLASSIFICATION_LIST = new ArrayList<StatusBean<String, String>>();
		TXT_CLASSIFICATION_LIST.add(new StatusBean<String, String>(TXT_CLASSIFICATION_PUBLIC_BASE, "公共基础课"));
		TXT_CLASSIFICATION_LIST.add(new StatusBean<String, String>(TXT_CLASSIFICATION_PROFESSION_ATTAINMENT, "职业素养课"));
	}

	public static void initClassificationMap() {
		TXT_CLASSIFICATION_MAP = StatusKit.toMap(TXT_CLASSIFICATION_LIST);
	}

	public static void initTextbookCoverList() {
		TXT_COVER_LIST = new ArrayList<StatusBean<String, String>>();
		TXT_COVER_LIST.add(new StatusBean<String, String>(COVER_0, "封面0"));
		TXT_COVER_LIST.add(new StatusBean<String, String>(COVER_1, "封面1"));
		TXT_COVER_LIST.add(new StatusBean<String, String>(COVER_2, "封面2"));
		TXT_COVER_LIST.add(new StatusBean<String, String>(COVER_3, "封面3"));
		TXT_COVER_LIST.add(new StatusBean<String, String>(COVER_4, "封面4"));
		TXT_COVER_LIST.add(new StatusBean<String, String>(COVER_5, "封面5"));
		TXT_COVER_LIST.add(new StatusBean<String, String>(COVER_6, "封面6"));
		TXT_COVER_LIST.add(new StatusBean<String, String>(COVER_7, "封面7"));
		TXT_COVER_LIST.add(new StatusBean<String, String>(COVER_8, "封面8"));
		TXT_COVER_LIST.add(new StatusBean<String, String>(COVER_9, "封面9"));

	}

	public static void initTextbookCoverMap() {
		TXT_COVER_MAP = StatusKit.toMap(TXT_COVER_LIST);
	}

	static {
		initTextbookList();
		initTextbookMap();

		initClassificationList();
		initClassificationMap();

		initTextbookCoverList();
		initTextbookCoverMap();

		initClassList();
		initClassMap();

		initTypeList();
		initTypeMap();

		initSellingStatusList();
		initSellingStatusMap();

	}
}
