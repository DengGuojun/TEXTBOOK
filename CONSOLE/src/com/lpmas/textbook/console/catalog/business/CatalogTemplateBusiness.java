package com.lpmas.textbook.console.catalog.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.textbook.catalog.bean.CatalogTemplateBean;
import com.lpmas.textbook.console.catalog.dao.CatalogTemplateDao;

public class CatalogTemplateBusiness {
	public int addCatalogTemplate(CatalogTemplateBean bean) {
		CatalogTemplateDao dao = new CatalogTemplateDao();
		return dao.insertCatalogTemplate(bean);
	}

	public int updateCatalogTemplate(CatalogTemplateBean bean) {
		CatalogTemplateDao dao = new CatalogTemplateDao();
		return dao.updateCatalogTemplate(bean);
	}

	public CatalogTemplateBean getCatalogTemplateByKey(int catalogId, String language, String templateType) {
		CatalogTemplateDao dao = new CatalogTemplateDao();
		return dao.getCatalogTemplateByKey(catalogId, language, templateType);
	}

	public PageResultBean<CatalogTemplateBean> getCatalogTemplatePageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		CatalogTemplateDao dao = new CatalogTemplateDao();
		return dao.getCatalogTemplatePageListByMap(condMap, pageBean);
	}

	public List<CatalogTemplateBean> getCatalogTemplateListByKey(int catalogId, String language) {
		CatalogTemplateDao dao = new CatalogTemplateDao();
		return dao.getCatalogTemplateListByKey(catalogId, language);
	}

}