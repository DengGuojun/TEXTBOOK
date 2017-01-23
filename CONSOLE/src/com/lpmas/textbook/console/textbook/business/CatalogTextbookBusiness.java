package com.lpmas.textbook.console.textbook.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.textbook.console.textbook.dao.CatalogTextbookDao;
import com.lpmas.textbook.textbook.bean.CatalogTextbookBean;

public class CatalogTextbookBusiness {
	public int addCatalogTextbook(CatalogTextbookBean bean) {
		CatalogTextbookDao dao = new CatalogTextbookDao();
		return dao.insertCatalogTextbook(bean);
	}

	public int updateCatalogTextbook(CatalogTextbookBean bean) {
		CatalogTextbookDao dao = new CatalogTextbookDao();
		return dao.updateCatalogTextbook(bean);
	}

	public int updateCatalogTextbookByBookId(CatalogTextbookBean bean) {
		CatalogTextbookDao dao = new CatalogTextbookDao();
		return dao.updateCatalogTextbookByBookId(bean);
	}

	public CatalogTextbookBean getCatalogTextbookByKey(int catalogId, int textbookId) {
		CatalogTextbookDao dao = new CatalogTextbookDao();
		return dao.getCatalogTextbookByKey(catalogId, textbookId);
	}

	public List<CatalogTextbookBean> getCatalogTextbookListByCatalogId(int catalogId) {
		CatalogTextbookDao dao = new CatalogTextbookDao();
		return dao.getCatalogTextbookListByCatalogId(catalogId);
	}

	public CatalogTextbookBean getCatalogTextbookByBookId(int textbookId) {
		CatalogTextbookDao dao = new CatalogTextbookDao();
		return dao.getCatalogTextbookByBookId(textbookId);
	}

	public PageResultBean<CatalogTextbookBean> getCatalogTextbookPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		CatalogTextbookDao dao = new CatalogTextbookDao();
		return dao.getCatalogTextbookPageListByMap(condMap, pageBean);
	}

	public boolean isExistsCatalogTextbook(int catalogId, int textbookId) {
		CatalogTextbookBean cataTxtBean = getCatalogTextbookByKey(catalogId, textbookId);
		if (cataTxtBean == null) {
			return false;
		}
		return true;
	}

}