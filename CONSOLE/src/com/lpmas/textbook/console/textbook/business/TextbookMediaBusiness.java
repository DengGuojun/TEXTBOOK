package com.lpmas.textbook.console.textbook.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.textbook.console.textbook.dao.TextbookMediaDao;
import com.lpmas.textbook.textbook.bean.TextbookMediaBean;

public class TextbookMediaBusiness {
	public int addTextbookMedia(TextbookMediaBean bean) {
		TextbookMediaDao dao = new TextbookMediaDao();
		return dao.insertTextbookMedia(bean);
	}

	public int updateTextbookMedia(TextbookMediaBean bean) {
		TextbookMediaDao dao = new TextbookMediaDao();
		return dao.updateTextbookMedia(bean);
	}

	public int removeTextbookMediaById(int textbookId) {
		TextbookMediaDao dao = new TextbookMediaDao();
		return dao.removeTextbookMediaById(textbookId);
	}

	public List<TextbookMediaBean> getTextbookMediaListById(int textbookId) {
		TextbookMediaDao dao = new TextbookMediaDao();
		return dao.getTextbookMediaListById(textbookId);
	}

	public TextbookMediaBean getTextbookMediaByKey(int textbookId, String mediaType) {
		TextbookMediaDao dao = new TextbookMediaDao();
		return dao.getTextbookMediaByKey(textbookId, mediaType);
	}

	public PageResultBean<TextbookMediaBean> getTextbookMediaPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		TextbookMediaDao dao = new TextbookMediaDao();
		return dao.getTextbookMediaPageListByMap(condMap, pageBean);
	}

	public int getTextbookCoverNumById(int textbookId) {
		TextbookMediaDao dao = new TextbookMediaDao();
		return dao.getTextbookCoverNumById(textbookId);
	}

}