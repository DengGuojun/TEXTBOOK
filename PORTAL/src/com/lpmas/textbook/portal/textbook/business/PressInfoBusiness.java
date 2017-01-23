package com.lpmas.textbook.portal.textbook.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.textbook.portal.textbook.dao.PressInfoDao;
import com.lpmas.textbook.textbook.bean.PressInfoBean;

public class PressInfoBusiness {
	public List<PressInfoBean> getPressInfoListAll() {
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("stutas", Constants.STATUS_VALID + "");
		PressInfoDao dao = new PressInfoDao();
		return dao.getPressInfoListByMap(condMap);
	}

	public PressInfoBean getPressInfoByKey(int pressId) {
		PressInfoDao dao = new PressInfoDao();
		return dao.getPressInfoByKey(pressId);
	}
}
