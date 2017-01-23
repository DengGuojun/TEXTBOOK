package com.lpmas.textbook.console.textbook.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.textbook.textbook.bean.RotationInfoBean;
import com.lpmas.textbook.console.textbook.dao.RotationInfoDao;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;

public class RotationInfoBusiness {
	public int addRotationInfo(RotationInfoBean bean) {
		RotationInfoDao dao = new RotationInfoDao();
		return dao.insertRotationInfo(bean);
	}

	public int updateRotationInfo(RotationInfoBean bean) {
		RotationInfoDao dao = new RotationInfoDao();
		return dao.updateRotationInfo(bean);
	}

	public RotationInfoBean getRotationInfoByKey(int rotationId) {
		RotationInfoDao dao = new RotationInfoDao();
		return dao.getRotationInfoByKey(rotationId);
	}

	public PageResultBean<RotationInfoBean> getRotationInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		RotationInfoDao dao = new RotationInfoDao();
		return dao.getRotationInfoPageListByMap(condMap, pageBean);
	}

	public List<RotationInfoBean> getRotationInfoListByMap(HashMap<String, String> condMap) {
		RotationInfoDao dao = new RotationInfoDao();
		return dao.getRotationInfoListByMap(condMap);
	}

}