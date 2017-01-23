package com.lpmas.textbook.console.textbook.business;

import com.lpmas.textbook.textbook.bean.ArticleInfoBean;
import com.lpmas.textbook.console.textbook.dao.ArticleInfoDao;

import java.util.HashMap;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;

public class ArticleInfoBusiness {
	public int addArticleInfo(ArticleInfoBean bean) {
		ArticleInfoDao dao = new ArticleInfoDao();
		return dao.insertArticleInfo(bean);
	}

	public int updateArticleInfo(ArticleInfoBean bean) {
		ArticleInfoDao dao = new ArticleInfoDao();
		return dao.updateArticleInfo(bean);
	}

	public ArticleInfoBean getArticleInfoByKey(int articleId) {
		ArticleInfoDao dao = new ArticleInfoDao();
		return dao.getArticleInfoByKey(articleId);
	}

	public PageResultBean<ArticleInfoBean> getArticleInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		ArticleInfoDao dao = new ArticleInfoDao();
		return dao.getArticleInfoPageListByMap(condMap, pageBean);
	}

	public int removeArticleInfo(ArticleInfoBean bean) {
		int result = 0;
		ArticleInfoDao dao = new ArticleInfoDao();
		ArticleInfoBean originalBean = dao.getArticleInfoByKey(bean.getArticleId());
		originalBean.setModifyUser(bean.getModifyUser());
		originalBean.setStatus(Constants.STATUS_NOT_VALID);
		result = dao.updateArticleInfo(originalBean);
		return result;
	}

}