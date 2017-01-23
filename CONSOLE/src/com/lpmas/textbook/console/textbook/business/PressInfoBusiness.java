package com.lpmas.textbook.console.textbook.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.textbook.console.textbook.dao.PressInfoDao;
import com.lpmas.textbook.textbook.bean.PressInfoBean;
import com.lpmas.textbook.textbook.bean.TextbookInfoBean;
import com.lpmas.textbook.textbook.config.TextbookInfoConfig;

public class PressInfoBusiness {
	public int addPressInfo(PressInfoBean bean) {
		PressInfoDao dao = new PressInfoDao();
		return dao.insertPressInfo(bean);
	}

	public int updatePressInfo(PressInfoBean bean) {
		PressInfoDao dao = new PressInfoDao();
		return dao.updatePressInfo(bean);
	}

	public PressInfoBean getPressInfoByKey(int pressId) {
		PressInfoDao dao = new PressInfoDao();
		return dao.getPressInfoByKey(pressId);
	}

	public List<PressInfoBean> getPressInfoListAll() {
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", Constants.STATUS_VALID + "");
		condMap.put("orderBy", "priority");
		PressInfoDao dao = new PressInfoDao();
		return dao.getPressInfoListByMap(condMap);
	}

	public PageResultBean<PressInfoBean> getPressInfoPageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		PressInfoDao dao = new PressInfoDao();
		return dao.getPressInfoPageListByMap(condMap, pageBean);
	}

	public int removePressInfo(PressInfoBean bean) {
		int result = 0;
		PressInfoDao dao = new PressInfoDao();
		PressInfoBean originalBean = dao.getPressInfoByKey(bean.getPressId());
		originalBean.setModifyUser(bean.getModifyUser());
		originalBean.setStatus(Constants.STATUS_NOT_VALID);
		result = dao.updatePressInfo(originalBean);

		return result;
	}

	public ReturnMessageBean verifyPressInfo(PressInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getPressName().trim())) {
			result.setMessage("名称必须填写");
		} else if (isExistsPressName(bean)) {
			result.setMessage("已存在相同的出版社名称");
		}
		return result;
	}

	// 判断是否存在的时候，要排除无效状态的用途
	public boolean isExistsPressName(PressInfoBean bean) {
		PressInfoBean existsBean = getPressInfoByNameWithStatus(bean.getPressName(), Constants.STATUS_VALID);
		if (existsBean == null) {
			return false;
		} else {
			if (bean.getPressId() == existsBean.getPressId()) {
				return false;
			}
		}
		return true;
	}

	private PressInfoBean getPressInfoByNameWithStatus(String pressName, int status) {
		PressInfoDao dao = new PressInfoDao();

		return dao.getPressInfoByNameWithStatus(pressName, status);
	}

	public ReturnMessageBean isReferenced(PressInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		HashMap<String, String> condMap = new HashMap<String, String>();
		TextbookInfoBusiness infoBusiness = new TextbookInfoBusiness();
		condMap.put("status", Constants.STATUS_VALID + "");
		condMap.put("descCode", TextbookInfoConfig.TXT_DESC_PRESS);
		condMap.put("descValue", bean.getPressId() + "");
		PageResultBean<TextbookInfoBean> resultBean = infoBusiness.getTextbookInfoPageListByMapConneDesc(condMap,
				new PageBean(1, 20));
		if (!resultBean.getRecordList().isEmpty()) {

			result.setMessage("此出版社被引用，不允许删除");
		}
		return result;
	}

}