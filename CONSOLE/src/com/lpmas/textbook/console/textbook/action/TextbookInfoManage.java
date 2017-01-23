package com.lpmas.textbook.console.textbook.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.ListKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.region.bean.ProvinceInfoBean;
import com.lpmas.region.client.RegionServiceClient;
import com.lpmas.textbook.catalog.bean.CatalogInfoBean;
import com.lpmas.textbook.console.catalog.business.CatalogInfoBusiness;
import com.lpmas.textbook.console.catalog.config.CatalogConsoleConfig;
import com.lpmas.textbook.console.config.TextbookConsoleConfig;
import com.lpmas.textbook.console.textbook.business.CatalogTextbookBusiness;
import com.lpmas.textbook.console.textbook.business.PressInfoBusiness;
import com.lpmas.textbook.console.textbook.business.TextbookDescriptionBusiness;
import com.lpmas.textbook.console.textbook.business.TextbookIndexBusiness;
import com.lpmas.textbook.console.textbook.business.TextbookInfoBusiness;
import com.lpmas.textbook.console.textbook.business.TextbookInfoUtil;
import com.lpmas.textbook.console.textbook.business.TextbookMediaBusiness;
import com.lpmas.textbook.console.textbook.config.TextbookConsoleTextbookConfig;
import com.lpmas.textbook.console.textbook.config.TextbookResource;
import com.lpmas.textbook.textbook.bean.CatalogTextbookBean;
import com.lpmas.textbook.textbook.bean.PressInfoBean;
import com.lpmas.textbook.textbook.bean.TextbookDescriptionBean;
import com.lpmas.textbook.textbook.bean.TextbookIndexBean;
import com.lpmas.textbook.textbook.bean.TextbookInfoBean;
import com.lpmas.textbook.textbook.bean.TextbookMediaBean;
import com.lpmas.textbook.textbook.config.TextbookInfoConfig;

@WebServlet("/textbook/TextbookInfoManage.do")
public class TextbookInfoManage extends HttpServlet {
	public static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request);
		int textbookId = ParamKit.getIntParameter(request, "textbookId", 0);
		TextbookInfoBean bean = new TextbookInfoBean();
		HashMap<Object, TextbookDescriptionBean> descMap = new HashMap<Object, TextbookDescriptionBean>();

		// 出版社
		List<PressInfoBean> pressList = new ArrayList<PressInfoBean>();
		PressInfoBusiness pressBusiness = new PressInfoBusiness();
		pressList = pressBusiness.getPressInfoListAll();

		CatalogInfoBusiness catalogBusiness = new CatalogInfoBusiness();

		List<TextbookMediaBean> coverImgList = new ArrayList<TextbookMediaBean>();
		if (textbookId > 0) {
			TextbookInfoBusiness business = new TextbookInfoBusiness();
			TextbookDescriptionBusiness descBusiness = new TextbookDescriptionBusiness();
			bean = business.getTextbookInfoByKey(textbookId);
			// description的信息
			descMap = (HashMap<Object, TextbookDescriptionBean>) ListKit
					.list2Map(descBusiness.getTextbookDescriptionListById(textbookId), "descCode");
			// 获取封面
			TextbookMediaBusiness mediaBusiness = new TextbookMediaBusiness();
			coverImgList = mediaBusiness.getTextbookMediaListById(textbookId);
			// 生成图片路径；
			for (TextbookMediaBean tempBean : coverImgList) {
				tempBean.setMediaUrl(TextbookConsoleConfig.RELATIVE_COVER_PATH + tempBean.getMediaUrl());
			}
		} else {
			if (!adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_INFO, OperationConfig.CREATE)) {
				HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
			bean.setSellingStatus(TextbookInfoConfig.SELLING_STATUS_ON);
		}

		List<CatalogInfoBean> directory1List = new ArrayList<CatalogInfoBean>();
		List<CatalogInfoBean> directory2List = new ArrayList<CatalogInfoBean>();
		int directory1 = -1;
		int directory2 = -1;
		// 第一级的专业list
		directory1List = catalogBusiness.getCatalogListByParentId(CatalogConsoleConfig.ROOT_PARENT_ID);
		// 第二级的专业list
		if (textbookId != 0) {
			HashMap<String, String> condMap = new HashMap<>();
			condMap.put("textbookId", String.valueOf(textbookId));
			TextbookDescriptionBusiness descriptionBusiness = new TextbookDescriptionBusiness();
			TextbookDescriptionBean textbookDescriptionBean = descriptionBusiness
					.getTextbookDescriptionByKey(textbookId, "CATALOGID");
			if (textbookDescriptionBean != null) {
				directory2 = Integer.valueOf(textbookDescriptionBean.getDescValue());
				CatalogInfoBean catalogInfoBean = catalogBusiness.getCatalogInfoByKey(directory2);
				//第一级专业
				if(catalogInfoBean.getParentCatalogId() == 1){
					directory1 = catalogInfoBean.getCatalogId();
					directory2List = catalogBusiness.getCatalogListByParentId(catalogInfoBean.getCatalogId());
				}else{
					directory1 = catalogInfoBean.getParentCatalogId();
					directory2List = catalogBusiness.getCatalogListByParentId(catalogInfoBean.getParentCatalogId());
				}
				
			}
		} else {
			CatalogInfoBean catalogInfoBean = directory1List.get(0);
			directory2List = catalogBusiness.getCatalogListByParentId(catalogInfoBean.getCatalogId());
		}

		// 省份
		RegionServiceClient client = new RegionServiceClient();
		List<ProvinceInfoBean> provinceList = client
				.getProvinceInfoListByCountryCode(TextbookConsoleTextbookConfig.CHN_NUM);

		request.setAttribute("directory1List", directory1List);
		request.setAttribute("directory2List", directory2List);
		request.setAttribute("directory1", directory1);
		request.setAttribute("directory2", directory2);
		request.setAttribute("pressInfoList", pressList);
		request.setAttribute("TextbookInfo", bean);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		request.setAttribute("descMap", descMap);
		request.setAttribute("provinceList", provinceList);
		request.setAttribute("coverImgList", coverImgList);

		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(TextbookConsoleTextbookConfig.PAGE_PATH + "TextbookInfoManage.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		TextbookInfoBean infoBean = new TextbookInfoBean();
		try {
			infoBean = BeanKit.request2Bean(request, TextbookInfoBean.class);
			TextbookInfoBusiness infoBusiness = new TextbookInfoBusiness();
			ReturnMessageBean messageBean = new ReturnMessageBean();
			int result = 0;
			if (infoBean.getTextbookId() > 0 && infoBean.getStatus() == Constants.STATUS_NOT_VALID) {// 删除
				infoBean.setModifyUser(adminUserHelper.getAdminUserId());
				result = infoBusiness.removeTextbookInfo(infoBean);
			} else {
				messageBean = infoBusiness.verifyTextbookInfo(infoBean);
				if (StringKit.isValid(messageBean.getMessage())) {
					HttpResponseKit.alertMessage(response, messageBean.getMessage(),
							HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				// 在更新或者新增的之前进行desc字段的输入校验，
				String price = ParamKit.getParameter(request, TextbookInfoConfig.TXT_DESC_PRICE);
				String overClass = ParamKit.getParameter(request, TextbookInfoConfig.TXT_DESC_OVERALL_CLASSIFICATION);
				String year = ParamKit.getParameter(request, TextbookInfoConfig.TXT_DESC_YEAR);
				// String txtClass = ParamKit.getParameter(request,
				// TextbookInfoConfig.TXT_DESC_TEXTBOOK_CLASS);
				String type = ParamKit.getParameter(request, TextbookInfoConfig.TXT_DESC_TEXTBOOK_TYPE);
				String groupEdit = ParamKit.getParameter(request, TextbookInfoConfig.TXT_DESC_GROUP_EDIT);
				String mainEdit = ParamKit.getParameter(request, TextbookInfoConfig.TXT_DESC_MAIN_EDIT);
				String press = ParamKit.getParameter(request, TextbookInfoConfig.TXT_DESC_PRESS);
				String pubDate = ParamKit.getParameter(request, TextbookInfoConfig.TXT_DESC_PUBLICATION_DATE);
				int calatlogId = ParamKit.getIntParameter(request, "CATALOGID", CatalogConsoleConfig.ROOT_PARENT_ID);

				if (!StringKit.isValid(price) || !TextbookInfoUtil.isPositive(price)) {
					HttpResponseKit.alertMessage(response, "请输入正确的价格", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				if (!StringKit.isValid(overClass)) {
					HttpResponseKit.alertMessage(response, "请选择总体分类", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				if (!StringKit.isValid(year)) {
					HttpResponseKit.alertMessage(response, "请选择年份", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				if (calatlogId == 1) {
					HttpResponseKit.alertMessage(response, "请选择专业分类", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				if (!StringKit.isValid(type)) {
					HttpResponseKit.alertMessage(response, "请选择图书类型", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				if (!StringKit.isValid(groupEdit)) {
					HttpResponseKit.alertMessage(response, "请填写组编", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				if (!StringKit.isValid(mainEdit)) {
					HttpResponseKit.alertMessage(response, "请填写主编", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				if (!StringKit.isValid(press)) {
					HttpResponseKit.alertMessage(response, "请选择出版社", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				if (!StringKit.isValid(pubDate)) {
					HttpResponseKit.alertMessage(response, "请填写出版日期", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}

				if (infoBean.getTextbookId() > 0) {
					if (!adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_INFO, OperationConfig.UPDATE)) {
						return;
					}
					infoBean.setModifyUser(adminUserHelper.getAdminUserId());
					result = infoBusiness.updateTextbookInfo(infoBean);

				} else {
					if (!adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_INFO, OperationConfig.CREATE)) {
						HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
						return;
					}
					infoBean.setCreateUser(adminUserHelper.getAdminUserId());
					result = infoBusiness.addTextbookInfo(infoBean);
					infoBean = infoBusiness.getTextbookInfoByNameWithStatus(infoBean.getTextbookName(),
							Constants.STATUS_VALID);
				}

				int textbookId = infoBean.getTextbookId();

				// 封面图片存储
				TextbookMediaBusiness mediaBusiness = new TextbookMediaBusiness();
				String imgCol = ParamKit.getParameter(request, "imgCol");

				List<TextbookMediaBean> coverList = mediaBusiness.getTextbookMediaListById(textbookId);
				// 有图片的情况下，先从数据库中删除记录
				if (coverList.size() > 0) {
					mediaBusiness.removeTextbookMediaById(textbookId);
				}
				if (StringKit.isValid(imgCol)) {
					String[] coverImg = imgCol.split(",");

					// 进行录入
					// 目前传进来的是图片连接是\asdasda\asdasdadadasd.jpg只取最后一个元素
					for (int i = 0; i < coverImg.length; i++) {
						int relativeLength = TextbookConsoleConfig.RELATIVE_COVER_PATH.length();
						TextbookMediaBean mediaBean = new TextbookMediaBean();
						mediaBean.setTextbookId(textbookId);
						mediaBean.setMediaType(String.valueOf(i));
						mediaBean.setMediaUrl(coverImg[i].substring(relativeLength));
						mediaBusiness.addTextbookMedia(mediaBean);
					}
				}

				// 将描述记录到description表里面
				TextbookDescriptionBusiness descBusiness = new TextbookDescriptionBusiness();
				for (StatusBean<String, String> tempBean : TextbookInfoConfig.TXT_DESC_LIST) {
					TextbookDescriptionBean descBean = new TextbookDescriptionBean();
					String descValue = ParamKit.getParameter(request, tempBean.getStatus());

					// 对价格进行两位小数的规范化格式
					if (TextbookInfoConfig.TXT_DESC_PRICE.equals(tempBean.getStatus())) {
						descValue = TextbookInfoUtil.regularPrice(descValue);
					}
					descBean.setTextbookId(textbookId);
					descBean.setDescCode(tempBean.getStatus());
					descBean.setDescValue(descValue);
					// 判断是执行更新还是新增操作
					if (descBusiness.isExistsDescription(textbookId, tempBean.getStatus())) {// 存在，则执行更新
						descBusiness.updateTextbookDescription(descBean);
					} else {
						descBusiness.addTextbookDescription(descBean);
					}

				}
				if (result > 0) {
					// 待所有记录都存进了数据库，进行solr记录
					List<TextbookDescriptionBean> list = descBusiness.getTextbookDescriptionListById(textbookId);
					TextbookIndexBean indexBean = new TextbookIndexBean();
					TextbookIndexBusiness indexBusiness = new TextbookIndexBusiness();
					indexBean.setId(String.valueOf(textbookId));
					indexBean.setTextbookName(infoBean.getTextbookName());
					indexBean.setPriority(infoBean.getPriority());
					indexBean.setSellingStatus(TextbookInfoConfig.SELLING_STATUS_MAP.get(infoBean.getSellingStatus()));
					indexBean.setCatalogId(calatlogId);
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
						}
					}

					// 图片

					List<TextbookMediaBean> coverImgList = new ArrayList<TextbookMediaBean>();
					coverImgList = mediaBusiness.getTextbookMediaListById(textbookId);
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
					// 需要从数据库中取出已经生成好时间的bean
					infoBean = infoBusiness.getTextbookInfoByKey(infoBean.getTextbookId());
					indexBean.setCreateTime(infoBean.getCreateTime().getTime());
					indexBean.setModifyTime(infoBean.getModifyTime() != null ? infoBean.getModifyTime().getTime() : 0);
					indexBean.setCreateUser(infoBean.getCreateUser());
					indexBean.setModifyUser(infoBean.getModifyUser());
					indexBusiness.updateTextbookIndex(indexBean);
				}
			} // 新增或者更新
			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/textbook/TextbookInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
