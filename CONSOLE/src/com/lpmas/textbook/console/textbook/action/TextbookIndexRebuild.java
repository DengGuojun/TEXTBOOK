package com.lpmas.textbook.console.textbook.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.NumberKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.textbook.console.textbook.business.TextbookDescriptionBusiness;
import com.lpmas.textbook.console.textbook.business.TextbookIndexBusiness;
import com.lpmas.textbook.console.textbook.business.TextbookInfoBusiness;
import com.lpmas.textbook.console.textbook.business.TextbookMediaBusiness;
import com.lpmas.textbook.console.textbook.config.TextbookResource;
import com.lpmas.textbook.textbook.bean.TextbookDescriptionBean;
import com.lpmas.textbook.textbook.bean.TextbookIndexBean;
import com.lpmas.textbook.textbook.bean.TextbookInfoBean;
import com.lpmas.textbook.textbook.bean.TextbookMediaBean;
import com.lpmas.textbook.textbook.config.TextbookInfoConfig;

@WebServlet("/textbook/TextbookIndexRebuild.do")
public class TextbookIndexRebuild extends HttpServlet {
	public static final long serialVersionUID = 1L;
	// private static Logger log =
	// LoggerFactory.getLogger(TextbookIndexRebuild.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		int result = 0;
		if (!adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_INDEX, OperationConfig.CREATE)) {
			HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		// 删除所有索引
		TextbookIndexBusiness indexBusiness = new TextbookIndexBusiness();
		String query = "id:*";

		result = indexBusiness.deleteTextbookByQuery(query);
		int count = 0;
		if (result >= 0) {// 进行重建索引
			TextbookInfoBusiness infoBusiness = new TextbookInfoBusiness();
			TextbookDescriptionBusiness descBusiness = new TextbookDescriptionBusiness();
			TextbookMediaBusiness mediaBusiness = new TextbookMediaBusiness();
			HashMap<String, String> condMap = new HashMap<String, String>();
			condMap.put("status", Constants.STATUS_VALID + "");
			List<TextbookInfoBean> infoList = infoBusiness.getTextbookInfoListByMap(condMap);

			for (TextbookInfoBean infoBean : infoList) {
				TextbookIndexBean indexBean = new TextbookIndexBean();

				indexBean.setId(infoBean.getTextbookId() + "");
				indexBean.setTextbookName(infoBean.getTextbookName());
				indexBean.setPriority(infoBean.getPriority());
				indexBean.setSellingStatus(TextbookInfoConfig.SELLING_STATUS_MAP.get(infoBean.getSellingStatus()));

				indexBean.setCreateTime(infoBean.getCreateTime().getTime());
				indexBean.setModifyTime(infoBean.getModifyTime() != null ? infoBean.getModifyTime().getTime() : 0);
				indexBean.setCreateUser(infoBean.getCreateUser());
				indexBean.setModifyUser(infoBean.getModifyUser());

				List<TextbookDescriptionBean> list = descBusiness
						.getTextbookDescriptionListById(infoBean.getTextbookId());
				for (TextbookDescriptionBean tempBean : list) {
					if (TextbookInfoConfig.TXT_DESC_PRICE.equals(tempBean.getDescCode())) {
						indexBean.setPrice(tempBean.getDescValue());
					} else if (TextbookInfoConfig.TXT_DESC_OVERALL_CLASSIFICATION.equals(tempBean.getDescCode())) {
						indexBean.setOverClassification(tempBean.getDescValue());
					} else if (TextbookInfoConfig.TXT_DESC_YEAR.equals(tempBean.getDescCode())) {
						indexBean.setYear(tempBean.getDescValue());
					} else if (TextbookInfoConfig.TXT_DESC_PROVINCE.equals(tempBean.getDescCode())) {
						indexBean.setProvince(tempBean.getDescValue());
					} else if (TextbookInfoConfig.TXT_DESC_TEXTBOOK_CLASS.equals(tempBean.getDescCode())) {
						indexBean.setTextbookClass(tempBean.getDescValue());
					} else if (TextbookInfoConfig.TXT_DESC_TEXTBOOK_TYPE.equals(tempBean.getDescCode())) {
						indexBean.setTextbookType(tempBean.getDescValue());
					} else if (TextbookInfoConfig.TXT_DESC_GROUP_EDIT.equals(tempBean.getDescCode())) {
						indexBean.setGroupEdit(tempBean.getDescValue());
					} else if (TextbookInfoConfig.TXT_DESC_MAIN_EDIT.equals(tempBean.getDescCode())) {
						indexBean.setMainEdit(tempBean.getDescValue());
					} else if (TextbookInfoConfig.TXT_DESC_GUIDE_TEACHER.equals(tempBean.getDescCode())) {
						indexBean.setGuideTeacher(tempBean.getDescValue());
					} else if (TextbookInfoConfig.TXT_DESC_PRESS.equals(tempBean.getDescCode())) {
						indexBean.setPress(tempBean.getDescValue());
					} else if (TextbookInfoConfig.TXT_DESC_PUBLICATION_DATE.equals(tempBean.getDescCode())) {
						indexBean.setPublicationDate(tempBean.getDescValue());
					} else if (TextbookInfoConfig.TXT_DESC_BOOK_FORMAT.equals(tempBean.getDescCode())) {
						indexBean.setBookFormat(tempBean.getDescValue());
					} else if (TextbookInfoConfig.TXT_DESC_INTRODUCTION.equals(tempBean.getDescCode())) {
						indexBean.setIntroduction(tempBean.getDescValue());
					} else if (TextbookInfoConfig.TXT_DESC_WRITE_DESCRIPTION.equals(tempBean.getDescCode())) {
						indexBean.setWriteDescription(tempBean.getDescValue());
					} else if (TextbookInfoConfig.TXT_DESC_CONTENTS.equals(tempBean.getDescCode())) {
						indexBean.setContents(tempBean.getDescValue());
					} else if (TextbookInfoConfig.TXT_DESC_FIRST_CHAPTER.equals(tempBean.getDescCode())) {
						indexBean.setFirstChapter(tempBean.getDescValue());
					} else if (TextbookInfoConfig.TXT_DESC_CATALOGID.equals(tempBean.getDescCode())) {
						if (NumberKit.isPositiveInteger(tempBean.getDescValue())) {
							indexBean.setCatalogId(Integer.valueOf(tempBean.getDescValue()));
						}
					}
				}
				List<TextbookMediaBean> coverImgList = new ArrayList<TextbookMediaBean>();
				coverImgList = mediaBusiness.getTextbookMediaListById(infoBean.getTextbookId());
				int imgCount = 0;
				for (TextbookMediaBean coverBean : coverImgList) {
					if (imgCount == 0) {
						indexBean.setCover0(coverBean.getMediaUrl());
					} else if (imgCount == 1) {
						indexBean.setCover1(coverBean.getMediaUrl());
					} else if (imgCount == 2) {
						indexBean.setCover2(coverBean.getMediaUrl());
					} else if (imgCount == 3) {
						indexBean.setCover3(coverBean.getMediaUrl());
					} else if (imgCount == 4) {
						indexBean.setCover4(coverBean.getMediaUrl());
					} else if (imgCount == 5) {
						indexBean.setCover5(coverBean.getMediaUrl());
					} else if (imgCount == 6) {
						indexBean.setCover6(coverBean.getMediaUrl());
					} else if (imgCount == 7) {
						indexBean.setCover7(coverBean.getMediaUrl());
					} else if (imgCount == 8) {
						indexBean.setCover8(coverBean.getMediaUrl());
					} else if (imgCount == 9) {
						indexBean.setCover9(coverBean.getMediaUrl());
					}
					imgCount++;
				}
				int flag = indexBusiness.addTextbookIndex(indexBean);
				if (flag > 0) {
					count++;
				}
			}
			if (count == infoList.size()) {
				HttpResponseKit.alertMessage(response, "重建成功", HttpResponseKit.ACTION_NONE);
				return;
			}
		}
		HttpResponseKit.alertMessage(response, "重建失败，请重试", HttpResponseKit.ACTION_NONE);
	}
}
