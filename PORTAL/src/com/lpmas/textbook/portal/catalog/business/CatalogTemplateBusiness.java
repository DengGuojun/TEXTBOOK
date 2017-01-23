package com.lpmas.textbook.portal.catalog.business;

import java.util.List;

import com.lpmas.textbook.catalog.bean.CatalogInfoBean;
import com.lpmas.textbook.catalog.bean.CatalogTemplateBean;
import com.lpmas.textbook.portal.catalog.dao.CatalogTemplateDao;

public class CatalogTemplateBusiness {

	public CatalogTemplateBean getCatalogTemplateByKey(int catalogId, String language, String templateType) {
		CatalogTemplateDao dao = new CatalogTemplateDao();
		return dao.getCatalogTemplateByKey(catalogId, language, templateType);
	}

	public CatalogTemplateBean getCatalogTemplateByCatalogList(List<CatalogInfoBean> list, String language,
			String templateType) {
		CatalogTemplateBean bean = null;
		CatalogTemplateDao dao = new CatalogTemplateDao();
		for (int i = list.size() - 1; i >= 0; i--) {
			CatalogInfoBean catalogInfoBean = list.get(i);
			bean = dao.getCatalogTemplateByKey(catalogInfoBean.getCatalogId(), language, templateType);
			if (bean != null && bean.getTemplateId() > 0) {
				return bean;
			}
		}
		return bean;
	}
}