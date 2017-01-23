package com.lpmas.textbook.console.catalog.business;

import java.util.HashMap;

import com.lpmas.textbook.catalog.bean.CatalogInfoBean;
import com.lpmas.textbook.console.catalog.config.CatalogConsoleConfig;

public class CatalogInfoUtil {

	public static HashMap<String, CatalogInfoBean> getParentProgramType(CatalogInfoBean bean) {
		CatalogInfoBean firstParentBean = new CatalogInfoBean();
		CatalogInfoBean secondParentBean = new CatalogInfoBean();

		HashMap<String, CatalogInfoBean> parentMap = new HashMap<String, CatalogInfoBean>();
		CatalogInfoBean rootBean = new CatalogInfoBean();
		rootBean.setCatalogId(CatalogConsoleConfig.ROOT_PARENT_ID);
		rootBean.setCatalogName(CatalogConsoleConfig.ROOT_PARENT_NAME);
		rootBean.setParentCatalogId(-1);

		CatalogInfoBusiness business = new CatalogInfoBusiness();

		if (bean.getParentCatalogId() == CatalogConsoleConfig.ROOT_PARENT_ID) {// 根目录
			firstParentBean = rootBean;
		} else {
			CatalogInfoBean tempBean = business.getCatalogInfoByKey(bean.getParentCatalogId());
			if (tempBean.getParentCatalogId() == CatalogConsoleConfig.ROOT_PARENT_ID) {// 是有一层
				firstParentBean = tempBean;
			} else {
				secondParentBean = tempBean;
				firstParentBean = business.getCatalogInfoByKey(secondParentBean.getParentCatalogId());
			}
		}
		parentMap.put("firstParentBean", firstParentBean);
		parentMap.put("secondParentBean", secondParentBean);
		return parentMap;
	}

	/**
	 * 返回三级节点的值
	 * 
	 * @param bean
	 * @return
	 */
	public static HashMap<String, Integer> getParentCatalogInfoMap(CatalogInfoBean bean) {
		// 获取节点
		int catalogId = bean.getCatalogId();
		HashMap<String, Integer> selectMap = new HashMap<String, Integer>();
		selectMap.put("select1", 0);
		selectMap.put("select2", 0);
		selectMap.put("select3", 0);
		CatalogInfoBusiness business = new CatalogInfoBusiness();
		// 当前bean
		CatalogInfoBean catalogBean = business.getCatalogInfoByKey(catalogId);

		// 第一层节点
		if (catalogBean.getParentCatalogId() == CatalogConsoleConfig.ROOT_PARENT_ID) {
			selectMap.put("select1", catalogBean.getCatalogId());
		} else {
			// 第二层节点
			CatalogInfoBean _pb = business.getCatalogInfoByKey(catalogBean.getParentCatalogId());
			if (_pb.getParentCatalogId() == CatalogConsoleConfig.ROOT_PARENT_ID) {
				selectMap.put("select2", catalogBean.getCatalogId());
				selectMap.put("select1", _pb.getCatalogId());
			} else {
				// 第三层
				CatalogInfoBean __pb = business.getCatalogInfoByKey(_pb.getParentCatalogId());
				if (__pb.getParentCatalogId() == CatalogConsoleConfig.ROOT_PARENT_ID) {
					selectMap.put("select3", catalogBean.getCatalogId());
					selectMap.put("select2", _pb.getCatalogId());
					selectMap.put("select1", __pb.getCatalogId());
				}
			}

		}
		return selectMap;
	}
}
