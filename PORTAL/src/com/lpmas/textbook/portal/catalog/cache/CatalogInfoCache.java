package com.lpmas.textbook.portal.catalog.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.cache.LocalCache;
import com.lpmas.framework.config.Constants;
import com.lpmas.textbook.catalog.bean.CatalogInfoBean;
import com.lpmas.textbook.portal.catalog.business.CatalogInfoBusiness;
import com.lpmas.textbook.portal.catalog.config.CatalogCacheConfig;
import com.opensymphony.oscache.base.NeedsRefreshException;

public class CatalogInfoCache {
	private static Logger log = LoggerFactory.getLogger(CatalogInfoCache.class);

	public CatalogInfoBean getCatalogInfoByKey(int catalogId) {
		CatalogInfoBean bean = null;
		Object obj = null;
		String key = CatalogCacheConfig.getCatalogInfoByKey(catalogId);
		LocalCache localCache = LocalCache.getInstance();
		try {
			obj = localCache.get(key);
		} catch (NeedsRefreshException nre) {
			boolean updated = false;
			try {
				CatalogInfoBusiness business = new CatalogInfoBusiness();
				obj = business.getCatalogInfoByKey(catalogId);

				if (obj != null) {
					localCache.set(key, obj, Constants.CACHE_TIME_15_MIN);
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
			bean = (CatalogInfoBean) obj;
		}
		return bean;
	}

	/**
	 * 获取根目录
	 * 
	 * @return
	 */
	public CatalogInfoBean getRootCatalogInfo() {
		CatalogInfoBean bean = null;
		Object obj = null;
		String key = CatalogCacheConfig.getRootCatalogInfo();
		LocalCache localCache = LocalCache.getInstance();
		try {
			obj = localCache.get(key);
		} catch (NeedsRefreshException nre) {
			boolean updated = false;
			try {
				CatalogInfoBusiness business = new CatalogInfoBusiness();
				obj = business.getRootCatalogInfo();

				if (obj != null) {
					localCache.set(key, obj, Constants.CACHE_TIME_15_MIN);
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
			bean = (CatalogInfoBean) obj;
		}
		return bean;
	}

	public List<CatalogInfoBean> getCatalogInfoListByPath(String path) {
		List<CatalogInfoBean> list = null;
		Object obj = null;
		String key = CatalogCacheConfig.getCatalogInfoListByPathKey(path);
		LocalCache localCache = LocalCache.getInstance();
		try {
			obj = localCache.get(key);
		} catch (NeedsRefreshException nre) {
			boolean updated = false;
			try {
				CatalogInfoBusiness business = new CatalogInfoBusiness();
				obj = business.getCatalogInfoListByPath(path);

				if (obj != null) {
					localCache.set(key, obj, Constants.CACHE_TIME_15_MIN);
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
			list = (List<CatalogInfoBean>) obj;
		}
		return list;
	}

	public List<CatalogInfoBean> getCatalogInfoListByParentCatalogId(int parentCatalogId) {
		List<CatalogInfoBean> list = null;
		Object obj = null;
		String key = CatalogCacheConfig.getCatalogInfoListByParentCatalogId(parentCatalogId);
		LocalCache localCache = LocalCache.getInstance();
		try {
			obj = localCache.get(key);
		} catch (NeedsRefreshException nre) {
			boolean updated = false;
			try {
				CatalogInfoBusiness business = new CatalogInfoBusiness();
				obj = business.getCatalogInfoListByParentCatalogId(parentCatalogId, "priority desc");

				if (obj != null) {
					localCache.set(key, obj, Constants.CACHE_TIME_15_MIN);
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
			list = (List<CatalogInfoBean>) obj;
		}
		return list;
	}

	public List<CatalogInfoBean> getCatalogInfoListByCatalogId(int catalogId) {
		List<CatalogInfoBean> list = null;
		Object obj = null;
		String key = CatalogCacheConfig.getCatalogInfoListByCatalogIdKey(catalogId);
		LocalCache localCache = LocalCache.getInstance();
		try {
			obj = localCache.get(key);
		} catch (NeedsRefreshException nre) {
			boolean updated = false;
			try {
				CatalogInfoBusiness business = new CatalogInfoBusiness();
				obj = business.getCatalogInfoListByCatalogId(catalogId);

				if (obj != null) {
					localCache.set(key, obj, Constants.CACHE_TIME_15_MIN);
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
			list = (List<CatalogInfoBean>) obj;
		}
		return list;
	}
}
