package com.lpmas.textbook.console.textbook.business;

import com.lpmas.textbook.textbook.bean.FeedbackInfoBean;
import com.lpmas.textbook.console.textbook.dao.FeedbackInfoDao;

import java.util.HashMap;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;

public class FeedbackInfoBusiness {
	public int addFeedbackInfo(FeedbackInfoBean bean) {
		FeedbackInfoDao dao = new FeedbackInfoDao();
		return dao.insertFeedbackInfo(bean);
	}

	public int updateFeedbackInfo(FeedbackInfoBean bean) {
		FeedbackInfoDao dao = new FeedbackInfoDao();
		return dao.updateFeedbackInfo(bean);
	}

	public FeedbackInfoBean getFeedbackInfoByKey(int feedbackId) {
		FeedbackInfoDao dao = new FeedbackInfoDao();
		return dao.getFeedbackInfoByKey(feedbackId);
	}

	public PageResultBean<FeedbackInfoBean> getFeedbackInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		FeedbackInfoDao dao = new FeedbackInfoDao();
		return dao.getFeedbackInfoPageListByMap(condMap, pageBean);
	}

}