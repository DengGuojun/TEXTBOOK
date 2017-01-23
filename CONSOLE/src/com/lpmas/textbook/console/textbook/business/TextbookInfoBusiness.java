package com.lpmas.textbook.console.textbook.business;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.textbook.console.textbook.dao.TextbookInfoDao;
import com.lpmas.textbook.textbook.bean.TextbookIndexBean;
import com.lpmas.textbook.textbook.bean.TextbookInfoBean;
import com.lpmas.textbook.textbook.config.TextbookInfoConfig;

public class TextbookInfoBusiness {
	private static Logger log = LoggerFactory.getLogger(TextbookInfoBusiness.class);

	public int addTextbookInfo(TextbookInfoBean bean) {
		int result = 0;
		TextbookInfoDao dao = new TextbookInfoDao();
		result = dao.insertTextbookInfo(bean);

		return result;
	}

	public int updateTextbookInfo(TextbookInfoBean bean) {
		int result = 0;
		TextbookInfoDao dao = new TextbookInfoDao();
		result = dao.updateTextbookInfo(bean);

		return result;
	}

	public int removeTextbookInfo(TextbookInfoBean bean) {
		int result = 0;
		TextbookInfoBean originalBean = getTextbookInfoByKey(bean.getTextbookId());
		originalBean.setStatus(Constants.STATUS_NOT_VALID);
		TextbookInfoDao dao = new TextbookInfoDao();
		result = dao.updateTextbookInfo(originalBean);

		if (result > 0) { // 索引上删除
			TextbookIndexBusiness indexBusiness = new TextbookIndexBusiness();
			try {
				indexBusiness.removeTextbookIndex(bean.getTextbookId() + "");
			} catch (Exception e) {
				log.error("", e);
			}
		}
		return result;
	}

	public int offSellTextbook(TextbookInfoBean bean) {
		int result = 0;
		if (TextbookInfoConfig.SELLING_STATUS_OFF.equals(bean.getSellingStatus()))
			result = updateTextbookInfo(bean);

		if (result > 0) { // 进行索引删除
			TextbookIndexBusiness business = new TextbookIndexBusiness();
			try {
				TextbookIndexBean indexBean = business.getTextbookIndexById(bean.getTextbookId() + "");
				indexBean.setSellingStatus(
						TextbookInfoConfig.SELLING_STATUS_MAP.get(TextbookInfoConfig.SELLING_STATUS_OFF));
				business.updateTextbookIndex(indexBean);
			} catch (Exception e) {
				log.error("", e);
			}
		}
		return result;
	}

	public int onSellTextbook(TextbookInfoBean bean) {
		int result = 0;
		if (TextbookInfoConfig.SELLING_STATUS_ON.equals(bean.getSellingStatus()))
			result = updateTextbookInfo(bean);
		if (result > 0) { // 进行索引删除
			TextbookIndexBusiness business = new TextbookIndexBusiness();
			try {
				TextbookIndexBean indexBean = business.getTextbookIndexById(bean.getTextbookId() + "");
				indexBean.setSellingStatus(
						TextbookInfoConfig.SELLING_STATUS_MAP.get(TextbookInfoConfig.SELLING_STATUS_ON));
				business.updateTextbookIndex(indexBean);
			} catch (Exception e) {
				log.error("", e);
			}
		}
		return result;
	}

	public TextbookInfoBean getTextbookInfoByKey(int textbookId) {
		TextbookInfoDao dao = new TextbookInfoDao();
		return dao.getTextbookInfoByKey(textbookId);
	}

	public PageResultBean<TextbookInfoBean> getTextbookInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		TextbookInfoDao dao = new TextbookInfoDao();
		return dao.getTextbookInfoPageListByMap(condMap, pageBean);
	}

	public PageResultBean<TextbookInfoBean> getTextbookInfoPageListByMapConneCatalog(HashMap<String, String> condMap,
			PageBean pageBean) {
		TextbookInfoDao dao = new TextbookInfoDao();
		return dao.getTextbookInfoPageListByMapConneCatalog(condMap, pageBean);
	}

	public PageResultBean<TextbookInfoBean> getTextbookInfoPageListByMapConneDesc(HashMap<String, String> condMap,
			PageBean pageBean) {
		TextbookInfoDao dao = new TextbookInfoDao();
		return dao.getTextbookInfoPageListByMapConneDesc(condMap, pageBean);
	}

	public List<TextbookInfoBean> getTextbookInfoListByMap(HashMap<String, String> condMap) {
		TextbookInfoDao dao = new TextbookInfoDao();
		return dao.getTextbookInfoListByMap(condMap);
	}

	public ReturnMessageBean verifyTextbookInfo(TextbookInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getTextbookName().trim())) {
			result.setMessage("名称必须填写");
		} else if (isExistsTextbookName(bean)) {
			result.setMessage("已存在相同的名称");
		}
		return result;
	}

	private boolean isExistsTextbookName(TextbookInfoBean bean) {
		TextbookInfoBean existsBean = getTextbookInfoByNameWithStatus(bean.getTextbookName(), Constants.STATUS_VALID);
		if (existsBean == null) {
			return false;
		} else {
			if (bean.getTextbookId() == existsBean.getTextbookId()) {
				return false;
			}
		}
		return true;
	}

	public TextbookInfoBean getTextbookInfoByNameWithStatus(String textbookName, int status) {
		TextbookInfoDao dao = new TextbookInfoDao();
		return dao.getTextbookInfoByNameWithStatus(textbookName, status);
	}

}