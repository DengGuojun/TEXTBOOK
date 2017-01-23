package com.lpmas.textbook.console.textbook.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.textbook.console.textbook.dao.MediaInfoDao;
import com.lpmas.textbook.textbook.bean.MediaInfoBean;

public class MediaInfoBusiness {
	public int addMediaInfo(MediaInfoBean bean) {
		MediaInfoDao dao = new MediaInfoDao();
		return dao.insertMediaInfo(bean);
	}

	public int updateMediaInfo(MediaInfoBean bean) {
		MediaInfoDao dao = new MediaInfoDao();
		return dao.updateMediaInfo(bean);
	}

	public MediaInfoBean getMediaInfoByKey(int mediaId) {
		MediaInfoDao dao = new MediaInfoDao();
		return dao.getMediaInfoByKey(mediaId);
	}

	public MediaInfoBean getMediaInfoByPath(String path) {
		MediaInfoDao dao = new MediaInfoDao();
		return dao.getMediaInfoByPath(path);
	}

	public PageResultBean<MediaInfoBean> getMediaInfoPageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		MediaInfoDao dao = new MediaInfoDao();
		return dao.getMediaInfoPageListByMap(condMap, pageBean);
	}

	public List<MediaInfoBean> getMediaInfoList() {
		MediaInfoDao dao = new MediaInfoDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", Constants.STATUS_VALID + "");
		return dao.getMediaInfoList(condMap);
	}

}