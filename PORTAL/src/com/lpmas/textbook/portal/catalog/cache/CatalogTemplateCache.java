package com.lpmas.textbook.portal.catalog.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.cache.LocalCache;
import com.lpmas.framework.config.Constants;
import com.lpmas.textbook.catalog.bean.CatalogTemplateBean;
import com.lpmas.textbook.portal.catalog.business.CatalogTemplateBusiness;
import com.lpmas.textbook.portal.catalog.config.CatalogCacheConfig;
import com.opensymphony.oscache.base.NeedsRefreshException;

public class CatalogTemplateCache {
	private static Logger log = LoggerFactory.getLogger(CatalogTemplateCache.class);

	public CatalogTemplateBean getCatalogTemplateByKey(int catalogId, String language, String templateType) {
		CatalogTemplateBean bean = null;
		Object obj = null;
		String key = CatalogCacheConfig.getCatalogTemplateByKey(catalogId, language, templateType);
		LocalCache localCache = LocalCache.getInstance();
		try {
			obj = localCache.get(key);
		} catch (NeedsRefreshException nre) {
			boolean updated = false;
			try {
				CatalogTemplateBusiness business = new CatalogTemplateBusiness();
				obj = business.getCatalogTemplateByKey(catalogId, language, templateType);

				if (obj != null) {
					localCache.set(key, obj, Constants.CACHE_TIME_15_MIN);
					updated = true;
				} else {
					localCache.set(key, null, Constants.CACHE_TIME_15_MIN);
					updated = true;
				}
			} catch (Exception e) {
				log.error("", e);
			} finally {
				// 缓存更新失败
				if (!updated) {
					// 取得一个老的版本
					obj = nre.getCacheContent();
					// 取消更新
					localCache.cancelUpdate(key);
				}
			}

		}
		if (obj != null) {
			bean = (CatalogTemplateBean) obj;
		}
		return bean;
	}

}
