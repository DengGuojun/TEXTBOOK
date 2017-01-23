package com.lpmas.textbook.portal.textbook.business;

import com.lpmas.textbook.textbook.bean.FeedbackInfoBean;
import com.lpmas.textbook.portal.textbook.dao.FeedbackInfoDao;

public class FeedbackInfoBusiness {
	public int addFeedbackInfo(FeedbackInfoBean bean) {
		FeedbackInfoDao dao = new FeedbackInfoDao();
		return dao.insertFeedbackInfo(bean);
	}
}