package com.lpmas.textbook.portal.catalog.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.StringKit;
import com.lpmas.textbook.catalog.bean.CatalogInfoBean;
import com.lpmas.textbook.catalog.config.CatalogConfig;
import com.lpmas.textbook.portal.catalog.cache.CatalogInfoCache;
import com.lpmas.textbook.portal.catalog.dao.CatalogInfoDao;

public class CatalogInfoBusiness {
	public List<CatalogInfoBean> getCatalogListByParentId(int parentCatalogId) {
		CatalogInfoDao dao = new CatalogInfoDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("parentCatalogId", String.valueOf(parentCatalogId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		condMap.put("orderBy", "priority");
		return dao.getCatalogListByMap(condMap);
	}

	public List<CatalogInfoBean> getCatalogInfoListByParentCatalogId(int parentCatalogId, String orderBy) {
		CatalogInfoDao dao = new CatalogInfoDao();
		return dao.getCatalogInfoListByParentCatalogId(parentCatalogId, orderBy);
	}

	public CatalogInfoBean getCatalogInfoByKey(int catalogId) {
		CatalogInfoDao dao = new CatalogInfoDao();
		return dao.getCatalogInfoByKey(catalogId);
	}

	public CatalogInfoBean getRootCatalogInfo() {
		CatalogInfoDao dao = new CatalogInfoDao();
		return dao.getCatalogInfoByKey(1);
	}

	public List<CatalogInfoBean> getCatalogInfoListByCatalogId(int catalogId) {
		List<CatalogInfoBean> list = new ArrayList<CatalogInfoBean>();
		CatalogInfoCache cache = new CatalogInfoCache();
		CatalogInfoBean bean = cache.getCatalogInfoByKey(catalogId);
		if (bean != null) {
			list.add(0, bean);
			if (bean.getParentCatalogId() != CatalogConfig.ROOT_CATALOG_ID) {
				list.addAll(0, getCatalogInfoListByCatalogId(bean.getParentCatalogId()));
			}
		}
		return list;
	}

	public List<CatalogInfoBean> getCatalogInfoListByPath(String path) {
		List<CatalogInfoBean> list = new ArrayList<CatalogInfoBean>();

		CatalogInfoCache cache = new CatalogInfoCache();
		CatalogInfoBean rootBean = cache.getRootCatalogInfo();
		list.add(rootBean);
		if (StringKit.isValid(path)) {
			String[] pathArray = path.split("/");
			if (pathArray.length > 0) {
				if (rootBean == null) {// 没有根目录返回空列表
					list = new ArrayList<CatalogInfoBean>();
				} else {
					for (String catalogId : pathArray) {
						CatalogInfoBean bean = cache.getCatalogInfoByKey(Integer.valueOf(catalogId));
						if (bean == null) {// 没有目录返回空列表
							list = new ArrayList<CatalogInfoBean>();
							break;
						}
						list.add(bean);
					}
				}
			}
		}
		return list;
	}
}