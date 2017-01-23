package com.lpmas.textbook.portal.textbook.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.textbook.portal.textbook.dao.RotationInfoDao;
import com.lpmas.textbook.textbook.bean.RotationInfoBean;

public class RotationInfoBusiness {

	public List<RotationInfoBean> getRotationInfoAllList() {
		RotationInfoDao dao = new RotationInfoDao();
		HashMap<String, String> condMap = new HashMap<>();
		return dao.getRotationInfoListByMap(condMap);
	}

}