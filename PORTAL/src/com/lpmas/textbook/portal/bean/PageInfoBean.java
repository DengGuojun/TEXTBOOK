package com.lpmas.textbook.portal.bean;

import com.lpmas.constant.language.LanguageConfig;

public class PageInfoBean {
	int websiteId = 0;
	int storeId = 0;
	String language = LanguageConfig.LANG_CN;
	String pathInfo = "";
	String pageInfo = "";

	public int getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(int websiteId) {
		this.websiteId = websiteId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPathInfo() {
		return pathInfo;
	}

	public void setPathInfo(String pathInfo) {
		this.pathInfo = pathInfo;
	}

	public String getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(String pageInfo) {
		this.pageInfo = pageInfo;
	}

	@Override
	public String toString() {
		return "PageInfoBean [websiteId=" + websiteId + ", storeId=" + storeId + ", language=" + language
				+ ", pathInfo=" + pathInfo + ", pageInfo=" + pageInfo + "]";
	}

}
