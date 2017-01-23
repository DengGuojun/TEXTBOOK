package com.lpmas.textbook.console.textbook.business;

import java.util.HashMap;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.textbook.console.textbook.dao.TextbookIndexDao;
import com.lpmas.textbook.textbook.bean.TextbookIndexBean;
import com.lpmas.textbook.textbook.config.TextbookSolrConfig;

public class TextbookIndexBusiness {

	public int addTextbookIndex(TextbookIndexBean bean) {
		TextbookIndexDao dao = new TextbookIndexDao();
		return dao.addTextbookIndex(bean);
	}

	public int updateTextbookIndex(TextbookIndexBean bean) {
		TextbookIndexDao dao = new TextbookIndexDao();
		return dao.addTextbookIndex(bean);
	}

	public int removeTextbookIndex(String id) throws Exception {
		TextbookIndexDao dao = new TextbookIndexDao();
		return dao.removeTextbookIndexById(id);
	}

	public PageResultBean<TextbookIndexBean> getTextbookIndexPageListResult(SolrQuery query, PageBean pageBean,
			Class<TextbookIndexBean> clazz) {
		TextbookIndexDao dao = new TextbookIndexDao();
		return dao.getTextbookIndexPageListResult(TextbookSolrConfig.SOLR_HOME, TextbookSolrConfig.COLLECTION_TEXTBOOK,
				query, pageBean, clazz);
	}

	public List<TextbookIndexBean> getTextbookIndexListByQuery(SolrQuery query) {
		TextbookIndexDao dao = new TextbookIndexDao();
		return dao.getTextbookIndexListByQuery(query, TextbookIndexBean.class);
	}

	public int deleteTextbookByQuery(String query) {
		TextbookIndexDao dao = new TextbookIndexDao();
		return dao.deleteTextbookByQuery(query);
	}

	public TextbookIndexBean getTextbookIndexById(String id) {
		TextbookIndexDao dao = new TextbookIndexDao();
		return dao.getTextbookById(id);
	}

	public PageResultBean<TextbookIndexBean> getTextbookIndexPageListResult(HashMap<String, String> condMap,
			PageBean pageBean) {
		TextbookIndexDao dao = new TextbookIndexDao();
		return dao.getTextbookIndexPageListResult(condMap, pageBean);
	}
}
