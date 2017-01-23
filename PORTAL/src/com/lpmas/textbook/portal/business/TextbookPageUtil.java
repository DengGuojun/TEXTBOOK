package com.lpmas.textbook.portal.business;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.constant.language.LanguageConfig;
import com.lpmas.framework.util.StringKit;
import com.lpmas.textbook.portal.bean.PageInfoBean;

public class TextbookPageUtil {
	private static Logger log = LoggerFactory.getLogger(TextbookPageUtil.class);

	public static PageInfoBean parseRequestUrl(HttpServletRequest request) {
		PageInfoBean bean = new PageInfoBean();
		try {
			// String serverName = request.getServerName();
			String requestUri = java.net.URLDecoder.decode(request.getRequestURI(), "UTF-8");
			;

			// WebsiteInfoClientCache cache = new WebsiteInfoClientCache();
			// WebsiteInfoBean websiteBean =
			// cache.getWebsiteInfoByUrlMap().get(serverName);
			// bean.setWebsiteId(websiteBean.getWebsiteId());
			// bean.setStoreId(websiteBean.getStoreId());

			requestUri = requestUri.replaceAll("/+", "/");
			String[] uriArr = requestUri.split("/");

			if (uriArr.length >= 2) {
				String langFlag = uriArr[1].toUpperCase();
				if (LanguageConfig.LANGUAGE_MAP.containsKey(langFlag)) {
					bean.setLanguage(langFlag);
					requestUri = requestUri.substring(langFlag.length() + 1);
				}
			}
			int pose = requestUri.lastIndexOf("/");
			if (pose > 0) {
				bean.setPathInfo(requestUri.substring(1, pose));
			}

			String pageInfo = requestUri.substring(pose + 1);
			if (StringKit.isValid(pageInfo)) {
				pose = pageInfo.lastIndexOf(".");
				bean.setPageInfo(pageInfo.substring(0, pose));
			}
		} catch (Exception e) {
			log.error("parse url [{}] error:", request.getRequestURL(), e);
			e.printStackTrace();
			bean = new PageInfoBean();
		}
		return bean;
	}

}
