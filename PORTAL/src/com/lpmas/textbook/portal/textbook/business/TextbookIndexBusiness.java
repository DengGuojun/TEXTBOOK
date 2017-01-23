package com.lpmas.textbook.portal.textbook.business;

import java.util.HashMap;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.textbook.portal.textbook.dao.TextbookIndexDao;
import com.lpmas.textbook.textbook.bean.TextbookIndexBean;
import com.lpmas.textbook.textbook.config.TextbookInfoConfig;

public class TextbookIndexBusiness {
	public PageResultBean<TextbookIndexBean> getTextbookIndexPageListResult(HashMap<String, Object> condMap,
			PageBean pageBean) {
		TextbookIndexDao dao = new TextbookIndexDao();
		return dao.getTextbookIndexPageListResult(condMap, pageBean);
	}

	public List<TextbookIndexBean> getTextbookIndexListByQuery(SolrQuery query) {
		TextbookIndexDao dao = new TextbookIndexDao();
		return dao.getTextbookIndexListByQuery(query);
	}

	public TextbookIndexBean getTextbookIndexById(String id) {
		SolrQuery sq = new SolrQuery();
		String params = "sellingStatus: "
				+ TextbookInfoConfig.SELLING_STATUS_MAP.get(TextbookInfoConfig.SELLING_STATUS_ON) + " AND id:" + id;
		sq.setQuery(params);
		List<TextbookIndexBean> list = getTextbookIndexListByQuery(sq);

		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}

	}
}
