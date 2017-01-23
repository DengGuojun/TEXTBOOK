package com.lpmas.textbook.portal.textbook.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.textbook.portal.textbook.dao.KeywordInfoDao;
import com.lpmas.textbook.textbook.bean.KeywordInfoBean;

public class KeywordInfoBusiness {

	public List<KeywordInfoBean> getKeywordInfoListByMap(HashMap<String, String> condMap) {
		KeywordInfoDao dao = new KeywordInfoDao();
		return dao.getKeywordInfoListByMap(condMap);
	}

	public PageResultBean<KeywordInfoBean> getKeywordInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		KeywordInfoDao dao = new KeywordInfoDao();
		return dao.getKeywordInfoPageListByMap(condMap, pageBean);
	}

}