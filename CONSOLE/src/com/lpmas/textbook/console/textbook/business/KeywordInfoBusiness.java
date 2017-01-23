package com.lpmas.textbook.console.textbook.business;

import java.util.HashMap;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.textbook.console.textbook.dao.KeywordInfoDao;
import com.lpmas.textbook.textbook.bean.KeywordInfoBean;

public class KeywordInfoBusiness {
	public int addKeywordInfo(KeywordInfoBean bean) {
		KeywordInfoDao dao = new KeywordInfoDao();
		return dao.insertKeywordInfo(bean);
	}

	public int updateKeywordInfo(KeywordInfoBean bean) {
		KeywordInfoDao dao = new KeywordInfoDao();
		return dao.updateKeywordInfo(bean);
	}

	public KeywordInfoBean getKeywordInfoByKey(int keywordId) {
		KeywordInfoDao dao = new KeywordInfoDao();
		return dao.getKeywordInfoByKey(keywordId);
	}

	public PageResultBean<KeywordInfoBean> getKeywordInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		KeywordInfoDao dao = new KeywordInfoDao();
		return dao.getKeywordInfoPageListByMap(condMap, pageBean);
	}

	public ReturnMessageBean verifyKeywordInfo(KeywordInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getKeyword().trim())) {
			result.setMessage("名称必须填写");
		} else if (isExistsKeyword(bean)) {
			result.setMessage("已存在相同的关键字");
		}
		return result;
	}

	// 判断是否存在的时候，要排除无效状态的用途
	public boolean isExistsKeyword(KeywordInfoBean bean) {
		KeywordInfoBean existsBean = getKeywordInfoByNameWithStatus(bean.getKeyword(), Constants.STATUS_VALID);
		if (existsBean == null) {
			System.out.println("null");
			return false;
		} else {
			if (bean.getKeywordId() == existsBean.getKeywordId()) {
				return false;
			}
		}
		return true;
	}

	public KeywordInfoBean getKeywordInfoByNameWithStatus(String keyword, int status) {
		KeywordInfoDao dao = new KeywordInfoDao();

		return dao.getKeywordInfoByNameWithStatus(keyword, status);
	}

	public int removeKeywordInfo(KeywordInfoBean bean) {
		int result = 0;
		KeywordInfoDao dao = new KeywordInfoDao();
		KeywordInfoBean originalBean = dao.getKeywordInfoByKey(bean.getKeywordId());
		originalBean.setModifyUser(bean.getModifyUser());
		originalBean.setStatus(Constants.STATUS_NOT_VALID);
		result = dao.updateKeywordInfo(originalBean);

		return result;
	}

}