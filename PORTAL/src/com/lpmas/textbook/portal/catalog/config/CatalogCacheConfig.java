package com.lpmas.textbook.portal.catalog.config;

public class CatalogCacheConfig {
	public static final String CATALOG_INFO_BY_KEY = "CATALOG_INFO_BY_";
	public static final String CATALOG_INFO_LIST_BY_PARENT_CATALOG_ID_KEY = "CATALOG_INFO_LIST_BY_PARENT_CATALOG_ID_";
	public static final String CATALOG_INFO_LIST_BY_CATALOG_ID_KEY = "CATALOG_INFO_LIST_BY_CATALOG_ID_";
	public static final String CATALOG_INFO_LIST_BY_PATH_KEY = "CATALOG_INFO_LIST_BY_PATH_";

	public static final String ROOT_CATALOG_INFO = "ROOT_CATALOG_INFO";

	public static final String CATALOG_TAMPLATE_BY_KEY = "CATALOG_TAMPLATE_BY_";

	public static String getRootCatalogInfo() {
		return ROOT_CATALOG_INFO;
	}

	public static String getCatalogInfoListByCatalogIdKey(int catalogId) {
		return CATALOG_INFO_LIST_BY_CATALOG_ID_KEY + catalogId;
	}

	public static String getCatalogInfoListByPathKey(String path) {
		return CATALOG_INFO_LIST_BY_PATH_KEY + "_" + path;
	}

	public static String getCatalogInfoListByParentCatalogId(int parentCatalogId) {
		return CATALOG_INFO_LIST_BY_PARENT_CATALOG_ID_KEY + parentCatalogId;
	}

	public static String getCatalogInfoByKey(int catalogId) {
		return CATALOG_INFO_BY_KEY + catalogId;
	}

	public static String getCatalogTemplateByKey(int catalogId, String language, String templateType) {
		return CATALOG_TAMPLATE_BY_KEY + catalogId + "_" + language + "_" + templateType;
	}

}
