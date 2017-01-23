package com.lpmas.textbook.portal.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.tools.exception.PortalServletException;
import com.lpmas.framework.util.FreemarkerKit;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.template.bean.TemplateContentBean;
import com.lpmas.template.client.cache.TemplateContentClientCache;
import com.lpmas.textbook.catalog.bean.CatalogInfoBean;
import com.lpmas.textbook.catalog.bean.CatalogTemplateBean;
import com.lpmas.textbook.catalog.config.CatalogConfig;
import com.lpmas.textbook.config.TextbookConfig;
import com.lpmas.textbook.portal.bean.PageInfoBean;
import com.lpmas.textbook.portal.business.TextbookPageUtil;
import com.lpmas.textbook.portal.catalog.business.CatalogTemplateBusiness;
import com.lpmas.textbook.portal.catalog.cache.CatalogInfoCache;
import com.lpmas.textbook.portal.config.TextbookPortalConfig;
import com.lpmas.textbook.portal.config.TextbookPortalErrorCode;
import com.lpmas.textbook.portal.textbook.business.ArticleInfoBusiness;
import com.lpmas.textbook.portal.textbook.business.RotationInfoBusiness;
import com.lpmas.textbook.portal.textbook.business.TextbookIndexBusiness;
import com.lpmas.textbook.textbook.bean.ArticleInfoBean;
import com.lpmas.textbook.textbook.bean.RotationInfoBean;
import com.lpmas.textbook.textbook.bean.TextbookIndexBean;
import com.lpmas.textbook.textbook.config.ArticleInfoConfig;
import com.lpmas.textbook.textbook.config.TextbookInfoConfig;

/**
 * Servlet implementation class TextbookIndexPage
 */
@WebServlet(urlPatterns = { "*/index.shtml" })
public class TextbookIndexPage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(TextbookIndexPage.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 分析Url
		PageInfoBean pageBean = TextbookPageUtil.parseRequestUrl(request);
		// 根据路径获取销售目录列表
		CatalogInfoCache catalogInfoCache = new CatalogInfoCache();
		List<CatalogInfoBean> catalogInfoList = catalogInfoCache.getCatalogInfoListByPath(pageBean.getPathInfo());
		if (catalogInfoList == null || catalogInfoList.size() == 0) {
			throw new PortalServletException(TextbookConfig.APP_ID, TextbookPortalErrorCode.CATALOG_NOT_FOUND,
					"can not find catalog info");
		}
		log.info("pageBean = {},  catalogInfoList = {}", JsonKit.toJson(pageBean), JsonKit.toJson(catalogInfoList));

		CatalogInfoBean catalogInfoBean = catalogInfoList.get(catalogInfoList.size() - 1);

		// 根据销售目ID录查询销售目录下的销售目录模版
		CatalogTemplateBusiness catalogTemplateBusiness = new CatalogTemplateBusiness();
		CatalogTemplateBean catalogTemplateBean = catalogTemplateBusiness
				.getCatalogTemplateByCatalogList(catalogInfoList, pageBean.getLanguage(), CatalogConfig.CTT_INDEX);
		if (catalogTemplateBean == null) {
			throw new PortalServletException(TextbookConfig.APP_ID, TextbookPortalErrorCode.TEMPLATE_NOT_FOUND,
					"can not find catalog template");
		}

		// 根据销售目录模版内模版ID获取模版内容
		TemplateContentClientCache templateContentCache = new TemplateContentClientCache();
		TemplateContentBean templateContentBean = templateContentCache
				.getTemplateContentInUseStatusByTemplateId(catalogTemplateBean.getTemplateId());
		if (templateContentBean == null || templateContentBean.getTemplateId() == 0) {
			throw new PortalServletException(TextbookConfig.APP_ID, TextbookPortalErrorCode.TEMPLATE_NOT_FOUND,
					"can not find template");
		}

		// 获取子级分类列表
		List<CatalogInfoBean> subCatalogList = catalogInfoCache
				.getCatalogInfoListByParentCatalogId(catalogInfoBean.getCatalogId());

		// 获取最新图书
		List<TextbookIndexBean> topNewBookList = new ArrayList<TextbookIndexBean>();
		TextbookIndexBusiness textbookIndexBusiness = new TextbookIndexBusiness();
		HashMap<String, Object> condMap = new HashMap<String, Object>();
		condMap.put("sellingStatus", TextbookInfoConfig.SELLING_STATUS_MAP.get(TextbookInfoConfig.SELLING_STATUS_ON));
		condMap.put("orderBy", "publicationDate");
		PageBean page = new PageBean(1, 15);
		List<TextbookIndexBean> newBookList = textbookIndexBusiness.getTextbookIndexPageListResult(condMap, page)
				.getRecordList();
		// 分拆两个list，分别存储18本封面图书和列表图书
		topNewBookList = newBookList.subList(0, newBookList.size() > 15 ? 15 : newBookList.size());

		// 获取排行榜图书
		condMap.clear();
		condMap.put("sellingStatus", TextbookInfoConfig.SELLING_STATUS_MAP.get(TextbookInfoConfig.SELLING_STATUS_ON));
		condMap.put("orderBy", "priority");
		page = new PageBean(1, 5);
		List<TextbookIndexBean> hotBookList = textbookIndexBusiness.getTextbookIndexPageListResult(condMap, page)
				.getRecordList();

		FreemarkerKit freemarkerKit = new FreemarkerKit();
		HashMap<String, Object> contentMap = freemarkerKit.getContentMap();

		contentMap.put("TopNewBookList", topNewBookList);
		contentMap.put("HotBookList", hotBookList);
		contentMap.put("CatalogInfo", catalogInfoBean);
		contentMap.put("SubCatalogList", subCatalogList);
		contentMap.put("TextbookClassList", TextbookInfoConfig.TXT_CLASS_LIST);
		contentMap.put("CoverPath", TextbookPortalConfig.COVER_PATH);
		contentMap.put("RotationPath", TextbookPortalConfig.ROTATION_PATH);

		ArticleInfoBusiness articleInfoBusiness = new ArticleInfoBusiness();
		HashMap<String,String> stringCondMap = new HashMap<>();
		condMap.put("sectionType", ArticleInfoConfig.ANNOUNCEMENT);
		PageResultBean<ArticleInfoBean> AnnouncementPageList = articleInfoBusiness.getArtileInfo5List(stringCondMap);
		List<ArticleInfoBean> AnnouncementList = AnnouncementPageList.getRecordList();

		stringCondMap.clear();
		condMap.put("sectionType", ArticleInfoConfig.POLICY_HIGHLIGHTS);
		PageResultBean<ArticleInfoBean> PolicyHighlightsPageList = articleInfoBusiness.getArtileInfo5List(stringCondMap);
		List<ArticleInfoBean> PolicyHighlightsList = PolicyHighlightsPageList.getRecordList();

		contentMap.put("AnnouncementList", AnnouncementList);
		contentMap.put("PolicyHighlightsList", PolicyHighlightsList);

		RotationInfoBusiness rotationInfoBusiness = new RotationInfoBusiness();
		List<RotationInfoBean> RotationList = rotationInfoBusiness.getRotationInfoAllList();
		RotationList = RotationList.subList(0, RotationList.size() > 5 ? 5 : RotationList.size());
		contentMap.put("RotationList", RotationList);

		freemarkerKit.outputContent(contentMap, templateContentBean.getContent(), response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
