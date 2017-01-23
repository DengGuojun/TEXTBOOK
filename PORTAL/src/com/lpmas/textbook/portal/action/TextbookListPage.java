package com.lpmas.textbook.portal.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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
import com.lpmas.framework.web.ParamKit;
import com.lpmas.region.bean.ProvinceInfoBean;
import com.lpmas.region.client.RegionServiceClient;
import com.lpmas.template.bean.TemplateContentBean;
import com.lpmas.template.client.cache.TemplateContentClientCache;
import com.lpmas.textbook.catalog.bean.CatalogInfoBean;
import com.lpmas.textbook.catalog.bean.CatalogTemplateBean;
import com.lpmas.textbook.catalog.config.CatalogConfig;
import com.lpmas.textbook.config.TextbookConfig;
import com.lpmas.textbook.portal.bean.PageInfoBean;
import com.lpmas.textbook.portal.business.TextbookPageUtil;
import com.lpmas.textbook.portal.catalog.business.CatalogInfoBusiness;
import com.lpmas.textbook.portal.catalog.business.CatalogTemplateBusiness;
import com.lpmas.textbook.portal.catalog.cache.CatalogInfoCache;
import com.lpmas.textbook.portal.config.TextbookPortalConfig;
import com.lpmas.textbook.portal.config.TextbookPortalErrorCode;
import com.lpmas.textbook.portal.product.bean.ProductSearchFormBean;
import com.lpmas.textbook.portal.textbook.business.PressInfoBusiness;
import com.lpmas.textbook.portal.textbook.business.TextbookIndexBusiness;
import com.lpmas.textbook.portal.textbook.business.TextbookIndexUtil;
import com.lpmas.textbook.textbook.bean.PressInfoBean;
import com.lpmas.textbook.textbook.bean.TextbookIndexBean;
import com.lpmas.textbook.textbook.config.TextbookInfoConfig;

@WebServlet(urlPatterns = { "*/list.shtml", "*/list-*.shtml" })
public class TextbookListPage extends HttpServlet {
	public static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(TextbookListPage.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CatalogInfoBusiness catalogInfoBusiness = new CatalogInfoBusiness();
		TextbookIndexBusiness business = new TextbookIndexBusiness();

		PageInfoBean pageBean = TextbookPageUtil.parseRequestUrl(request);
		String searchBy = "NONE";
		// 查询模式判断
		if (pageBean.getPathInfo().startsWith("search/")) {
			searchBy = "text";
		} else if (pageBean.getPathInfo().startsWith("keySearch/")) {
			searchBy = "keySearch";
		}

		CatalogInfoCache catalogInfoCache = new CatalogInfoCache();
		List<CatalogInfoBean> catalogInfoList = new ArrayList<CatalogInfoBean>();
		// 根据店铺取根销售目录
		CatalogInfoBean rootCatalogInfoBean = catalogInfoCache.getRootCatalogInfo();
		catalogInfoList.add(rootCatalogInfoBean);

		if (catalogInfoList == null || catalogInfoList.size() == 0) {
			throw new PortalServletException(TextbookConfig.APP_ID, TextbookPortalErrorCode.CATALOG_NOT_FOUND,
					"can not find catalog info");
		}
		log.info("pageBean = {},  catalogInfoList = {}", JsonKit.toJson(pageBean), JsonKit.toJson(catalogInfoList));
		// 当前销售目录
		CatalogInfoBean catalogInfoBean = catalogInfoList.get(catalogInfoList.size() - 1);

		// 根据销售目ID录查询销售目录下的销售目录模版
		CatalogTemplateBusiness catalogTemplateBusiness = new CatalogTemplateBusiness();
		CatalogTemplateBean catalogTemplateBean = catalogTemplateBusiness
				.getCatalogTemplateByCatalogList(catalogInfoList, pageBean.getLanguage(), CatalogConfig.CTT_LIST);
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

		// 处理查询参数
		ProductSearchFormBean formBean = parseParam(pageBean);
		log.info("formBean = {}", JsonKit.toJson(formBean));

		int pageNum = formBean.getPageNum();
		int pageSize = formBean.getPageSize();
		PageBean page = new PageBean(pageNum, pageSize);

		FreemarkerKit freemarkerKit = new FreemarkerKit();
		HashMap<String, Object> contentMap = freemarkerKit.getContentMap();

		String searchWord = pageBean.getPathInfo().split("/")[1];

		String textbookName = ParamKit.getParameter(request, "textbookName", "*");
		String textbookClass = ParamKit.getParameter(request, "textbookClass", "*");
		String year = ParamKit.getParameter(request, "year", "*");
		String press = ParamKit.getParameter(request, "press", "*");
		String province = ParamKit.getParameter(request, "province", "*");

		HashMap<String, Object> condMap = new HashMap<>();
		condMap.put("textbookName", textbookName);
		condMap.put("textbookClass", textbookClass);
		condMap.put("year", year);
		condMap.put("press", press);
		condMap.put("province", province);

		if ("text".equals(searchBy)) {
			// 全文搜索关键字
			condMap.put("text", searchWord);
		} else {
			condMap.put("text", new String());
		}
		// 查询
		condMap.put("sellingStatus", TextbookInfoConfig.SELLING_STATUS_MAP.get(TextbookInfoConfig.SELLING_STATUS_ON));

		int catalogId = ParamKit.getIntParameter(request, "catalogId", CatalogConfig.ROOT_CATALOG_ID);
		CatalogInfoBean paramCatalogInfoBean = catalogInfoBusiness.getCatalogInfoByKey(catalogId);
		contentMap.put("DirectoryList1Id", CatalogConfig.ROOT_CATALOG_ID);
		contentMap.put("DirectoryList2Id", CatalogConfig.ROOT_CATALOG_ID);
		if (paramCatalogInfoBean != null) {
			int parentCatalogId = paramCatalogInfoBean.getParentCatalogId();
			if (parentCatalogId == 1) {
				List<CatalogInfoBean> catalogList = catalogInfoBusiness.getCatalogListByParentId(catalogId);
				List<Integer> catalogIdList = new ArrayList<>();
				catalogIdList.add(catalogId);
				for (CatalogInfoBean bean : catalogList) {
					catalogIdList.add(bean.getCatalogId());
				}
				condMap.put("catalogId", catalogIdList);
				contentMap.put("DirectoryList1Id", catalogId);
			} else {
				List<Integer> catalogIdList = new ArrayList<>();
				catalogIdList.add(catalogId);
				condMap.put("catalogId", catalogIdList);
				contentMap.put("DirectoryList1Id", paramCatalogInfoBean.getParentCatalogId());
				contentMap.put("DirectoryList2Id", catalogId);
			}
		}

		PageResultBean<TextbookIndexBean> result = business.getTextbookIndexPageListResult(condMap, page);
		page.init(pageNum, pageSize, result.getTotalRecordNumber());

		// 取出出版社的名字
		PressInfoBusiness pressBusiness = new PressInfoBusiness();
		List<PressInfoBean> pressList = pressBusiness.getPressInfoListAll();
		HashMap<String, String> pressMap = new HashMap<String, String>();
		for (PressInfoBean bean : pressList) {
			pressMap.put(String.valueOf(bean.getPressId()), bean.getPressName());
		}

		for (TextbookIndexBean bean : result.getRecordList()) {
			String pressCom = pressMap.get(bean.getPress());
			if (pressCom != null)
				bean.setPress(pressCom != null ? pressCom : "");
			// 处理内容介绍
			bean.setMemo(TextbookIndexUtil.getPartIntroduction(bean.getIntroduction()));
			// 处理图书类型
			bean.setTextbookClass(TextbookInfoConfig.TXT_CLASS_MAP.get(bean.getTextbookClass()));
		}
		// 输出全部参数
		for (Entry<String, Object> entry : condMap.entrySet()) {
			contentMap.put(entry.getKey(), entry.getValue());
		}

		// 搜索图书结果
		contentMap.put("TextbookList", result.getRecordList());
		contentMap.put("CatalogInfo", catalogInfoBean);
		contentMap.put("Page", page);
		// 导航栏的全文搜索
		contentMap.put("text", condMap.get("text"));
		contentMap.put("CoverPath", TextbookPortalConfig.COVER_PATH);

		// 专业分类
		List<CatalogInfoBean> DirectoryList1 = new ArrayList<>();
		DirectoryList1 = catalogInfoBusiness.getCatalogInfoListByParentCatalogId(1, "priority");
		contentMap.put("DirectoryList1", DirectoryList1);
		List<CatalogInfoBean> DirectoryList2 = new ArrayList<>();
		if (paramCatalogInfoBean != null) {
			if (paramCatalogInfoBean.getParentCatalogId() == 1) {
				DirectoryList2 = catalogInfoBusiness.getCatalogInfoListByParentCatalogId(catalogId, "priority");
			} else {
				DirectoryList2 = catalogInfoBusiness
						.getCatalogInfoListByParentCatalogId(paramCatalogInfoBean.getParentCatalogId(), "priority");
			}
		}
		contentMap.put("DirectoryList2", DirectoryList2);
		// 省份
		RegionServiceClient client = new RegionServiceClient();
		List<ProvinceInfoBean> provinceList = client.getProvinceInfoListByCountryCode("086");
		contentMap.put("provinceList", provinceList);
		// 出版社
		contentMap.put("pressList", pressList);
		freemarkerKit.outputContent(contentMap, templateContentBean.getContent(), response);
	}

	private ProductSearchFormBean parseParam(PageInfoBean pageBean) {
		ProductSearchFormBean bean = new ProductSearchFormBean();
		String[] params = pageBean.getPageInfo().split("-");

		if (params.length >= 2 && !"0".equals(params[1])) {// 页码
			bean.setPageNum(Integer.parseInt(params[1]));
		}
		if (params.length >= 3 && !"0".equals(params[2])) {// 每页条数
			bean.setPageSize(Integer.parseInt(params[2]));
		}
		if (bean.getPageNum() == 0) {
			bean.setPageNum(TextbookPortalConfig.DEFAULT_PAGE_NUM);
		}
		if (bean.getPageSize() == 0) {
			bean.setPageSize(TextbookPortalConfig.DEFAULT_PAGE_SIZE);
		}
		return bean;
	}

}
