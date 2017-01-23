package com.lpmas.textbook.console.textbook.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.textbook.console.textbook.dao.TextbookDescriptionDao;
import com.lpmas.textbook.textbook.bean.TextbookDescriptionBean;
import com.lpmas.textbook.textbook.bean.TextbookIndexBean;

public class TextbookDescriptionBusiness {
	public int addTextbookDescription(TextbookDescriptionBean bean) {
		int result = 0;
		TextbookDescriptionDao dao = new TextbookDescriptionDao();
		result = dao.insertTextbookDescription(bean);

		return result;
	}

	public int updateTextbookDescription(TextbookDescriptionBean bean) {
		int result = 0;
		TextbookDescriptionDao dao = new TextbookDescriptionDao();
		result = dao.updateTextbookDescription(bean);
		if (result > 0) {// 索引更新
			TextbookIndexBusiness indexBusiness = new TextbookIndexBusiness();
			TextbookIndexBean indexBean = indexBusiness.getTextbookIndexById(bean.getTextbookId() + "");

			indexBean.setTextbookClass(bean.getDescValue());

			indexBusiness.updateTextbookIndex(indexBean);
		}
		return result;
	}

	public TextbookDescriptionBean getTextbookDescriptionByKey(int textbookId, String descCode) {
		TextbookDescriptionDao dao = new TextbookDescriptionDao();
		return dao.getTextbookDescriptionByKey(textbookId, descCode);
	}

	public PageResultBean<TextbookDescriptionBean> getTextbookDescriptionPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		TextbookDescriptionDao dao = new TextbookDescriptionDao();
		return dao.getTextbookDescriptionPageListByMap(condMap, pageBean);
	}

	public List<TextbookDescriptionBean> getTextbookDescriptionListById(int textbookId) {
		TextbookDescriptionDao dao = new TextbookDescriptionDao();
		return dao.getTextbookDescriptionListById(textbookId);
	}

	public boolean isExistsDescription(int textbookId, String descCode) {
		TextbookDescriptionBean descBean = getTextbookDescriptionByKey(textbookId, descCode);
		if (descBean == null) {
			return false;
		}
		return true;
	}

}