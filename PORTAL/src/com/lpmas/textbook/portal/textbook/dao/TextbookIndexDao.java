package com.lpmas.textbook.portal.textbook.dao;

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

	public PageResultBean<TextbookIndexBean> getTextbookIndexPageListResult(HashMap<String, Object> condMap,
			PageBean pageBean) {
		SolrQuery query = new SolrQuery();

		String sellingStatus = (String) condMap.get("sellingStatus");
		StringBuilder params = new StringBuilder("sellingStatus:" + sellingStatus);

		String text = (String) condMap.get("text");
		if (StringKit.isValid(text)) {
			if (!text.contains("*")) {
				params.append(" AND text:\"" + text + "\"");
			}
		} else {
			String textbookName = (String) condMap.get("textbookName");
			if (StringKit.isValid(textbookName)) {
				if (!textbookName.contains("*")) {
					params.append(" AND textbookName:\"" + textbookName + "\"");
				}
			}
			String textbookClass = (String) condMap.get("textbookClass");
			if (StringKit.isValid(textbookClass)) {
				params.append(" AND textbookClass:" + textbookClass);
			}

			String year = (String) condMap.get("year");
			if (StringKit.isValid(year)) {
				if (!year.contains("*")) {
					params.append(" AND year:\"" + year + "\"");
				}
			}
			String press = (String) condMap.get("press");
			if (StringKit.isValid(press)) {
				params.append(" AND press:" + press);
			}
			String province = (String) condMap.get("province");
			if (StringKit.isValid(province)) {
				params.append(" AND province:" + province);
			}

			List<Integer> catalogIdList = (List<Integer>) condMap.get("catalogId");
			if (catalogIdList != null && !catalogIdList.isEmpty()) {
				params.append(" AND ( catalogId: " + catalogIdList.get(0));
				for (Integer i : catalogIdList.subList(1, catalogIdList.size())) {
					params.append(" OR catalogId:" + i);
				}
				params.append(" ) ");
			}
		}
		String publicationDate = (String) condMap.get("publicationDate");
		if (StringKit.isValid(publicationDate)) {
			params.append(" AND publicationDate:" + publicationDate);
		}
		String overClassification = (String) condMap.get("overClassification");
		if (StringKit.isValid(overClassification)) {
			params.append(" AND overClassification:" + overClassification);
		}

		// 排序
		String orderBy = (String) condMap.get("orderBy");
		if (StringKit.isValid(orderBy)) {
			query.addSort(orderBy, SolrQuery.ORDER.desc);
		} else {
			query.addSort("id", SolrQuery.ORDER.desc);
		}
		query.setQuery(params.toString());

		System.out.println("params : " + params.toString());

		return Solr.getInstance().getPageListResult(TextbookSolrConfig.SOLR_HOME,
				TextbookSolrConfig.COLLECTION_TEXTBOOK, query, pageBean, TextbookIndexBean.class);
	}

	public List<TextbookIndexBean> getTextbookIndexListByQuery(SolrQuery query) {

		return Solr.getInstance().getRecordListResult(TextbookSolrConfig.SOLR_HOME,
				TextbookSolrConfig.COLLECTION_TEXTBOOK, query, TextbookIndexBean.class);
	}

	public TextbookIndexBean getTextbookById(String id) {
		return Solr.getInstance().getRecordById(TextbookSolrConfig.SOLR_HOME, TextbookSolrConfig.COLLECTION_TEXTBOOK,
				id, TextbookIndexBean.class);
	}
}
