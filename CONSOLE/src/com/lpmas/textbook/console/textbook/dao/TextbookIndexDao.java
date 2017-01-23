package com.lpmas.textbook.console.textbook.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;

import com.lpmas.framework.index.solr.Solr;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.textbook.textbook.bean.TextbookIndexBean;
import com.lpmas.textbook.textbook.config.TextbookSolrConfig;

public class TextbookIndexDao {

	public int addTextbookIndex(TextbookIndexBean bean) {
		return Solr.getInstance().add(TextbookSolrConfig.SOLR_HOME, TextbookSolrConfig.COLLECTION_TEXTBOOK, bean);
	}

	public int removeTextbookIndexById(String id) {
		return Solr.getInstance().deleteById(TextbookSolrConfig.SOLR_HOME, TextbookSolrConfig.COLLECTION_TEXTBOOK, id);
	}

	public PageResultBean<TextbookIndexBean> getTextbookIndexPageListResult(String solrName, String collection,
			SolrQuery query, PageBean pageBean, Class<TextbookIndexBean> clazz) {
		return Solr.getInstance().getPageListResult(solrName, collection, query, pageBean, clazz);
	}

	public List<TextbookIndexBean> getTextbookIndexListByQuery(SolrQuery query, Class<TextbookIndexBean> clazz) {
		return Solr.getInstance().getRecordListResult(TextbookSolrConfig.SOLR_HOME,
				TextbookSolrConfig.COLLECTION_TEXTBOOK, query, clazz);
	}

	public int deleteTextbookByQuery(String query) {
		return Solr.getInstance().deleteByQuery(TextbookSolrConfig.SOLR_HOME, TextbookSolrConfig.COLLECTION_TEXTBOOK,
				query);
	}

	public TextbookIndexBean getTextbookById(String id) {
		return Solr.getInstance().getRecordById(TextbookSolrConfig.SOLR_HOME, TextbookSolrConfig.COLLECTION_TEXTBOOK,
				id, TextbookIndexBean.class);
	}

	public PageResultBean<TextbookIndexBean> getTextbookIndexPageListResult(HashMap<String, String> condMap,
			PageBean pageBean) {
		SolrQuery query = new SolrQuery();

		String textbookName = condMap.get("textbookName");
		StringBuilder params = new StringBuilder("textbookName:" + textbookName + "*");

		String textbookClass = condMap.get("textbookClass");
		if (StringKit.isValid(textbookClass)) {
			params.append(" AND textbookClass:" + textbookClass);
		}
		String year = condMap.get("year");
		if (StringKit.isValid(year)) {
			params.append(" AND year:" + year);
		}
		String press = condMap.get("press");
		if (StringKit.isValid(press)) {
			params.append(" AND press:" + press);
		}
		String province = condMap.get("province");
		if (StringKit.isValid(province)) {
			params.append(" AND province:" + province);
		}
		// 排序
		String orderBy = condMap.get("orderBy");
		if (StringKit.isValid(orderBy)) {
			query.addSort(orderBy, SolrQuery.ORDER.desc);
		} else {
			query.addSort("id", SolrQuery.ORDER.desc);
		}
		query.setQuery(params.toString());
		System.out.println(query.getQuery());
		return Solr.getInstance().getPageListResult(TextbookSolrConfig.SOLR_HOME,
				TextbookSolrConfig.COLLECTION_TEXTBOOK, query, pageBean, TextbookIndexBean.class);
	}
}
