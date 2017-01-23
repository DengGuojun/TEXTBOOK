package com.lpmas.textbook.portal.textbook.business;

import com.lpmas.textbook.textbook.bean.ArticleInfoBean;

import java.util.HashMap;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.textbook.portal.textbook.dao.ArticleInfoDao;

public class ArticleInfoBusiness {

	public ArticleInfoBean getArticleInfoByKey(int articleId) {
		ArticleInfoDao dao = new ArticleInfoDao();
		return dao.getArticleInfoByKey(articleId);
	}

	/**
	 * @return 五条显示在标题的文章
	 */
	public PageResultBean<ArticleInfoBean> getArtileInfo5List(HashMap<String, String> condMap) {
		ArticleInfoDao dao = new ArticleInfoDao();
		PageBean pageBean = new PageBean(1, 5);
		return dao.getRotationInfoPageListByMap(condMap, pageBean);
	}

}