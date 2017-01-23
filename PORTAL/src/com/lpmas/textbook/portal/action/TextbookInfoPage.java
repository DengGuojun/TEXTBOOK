package com.lpmas.textbook.portal.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.tools.exception.PortalServletException;
import com.lpmas.framework.util.FreemarkerKit;
import com.lpmas.framework.util.JsonKit;
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
import com.lpmas.textbook.portal.textbook.business.PressInfoBusiness;
import com.lpmas.textbook.portal.textbook.business.TextbookIndexBusiness;
import com.lpmas.textbook.portal.textbook.business.TextbookIndexUtil;
import com.lpmas.textbook.textbook.bean.PressInfoBean;
import com.lpmas.textbook.textbook.bean.TextbookIndexBean;
import com.lpmas.textbook.textbook.config.TextbookInfoConfig;

@WebServlet(urlPatterns = { "/textbook/*.shtml", "/t/*.shtml" })
public class TextbookInfoPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(TextbookInfoPage.class);

	public TextbookInfoPage() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PageInfoBean pageBean = TextbookPageUtil.parseRequestUrl(request);
		pageBean.setPathInfo("");
		CatalogInfoCache catalogInfoCache = new CatalogInfoCache();
		List<CatalogInfoBean> catalogInfoList = catalogInfoCache.getCatalogInfoListByPath(pageBean.getPathInfo());

		if (catalogInfoList == null || catalogInfoList.size() == 0) {
			throw new PortalServletException(TextbookConfig.APP_ID, TextbookPortalErrorCode.CATALOG_NOT_FOUND,
					"can not find catalog info");
		}
		log.info("pageBean = {},  catalogInfoList = {}", JsonKit.toJson(pageBean), JsonKit.toJson(catalogInfoList));

		// 根据销售目ID录查询销售目录下的销售目录模版
		CatalogTemplateBusiness catalogTemplateBusiness = new CatalogTemplateBusiness();
		CatalogTemplateBean catalogTemplateBean = catalogTemplateBusiness
				.getCatalogTemplateByCatalogList(catalogInfoList, pageBean.getLanguage(), CatalogConfig.CTT_PRODUCT);
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

		//
		TextbookIndexBusiness business = new TextbookIndexBusiness();
		TextbookIndexBean indexBean = business.getTextbookIndexById(pageBean.getPageInfo());

		HashMap<String, String> txtclass = TextbookInfoConfig.TXT_CLASS_MAP;
		String textbookClass = "";
		if (indexBean != null) {
			textbookClass = txtclass.get(indexBean.getTextbookClass());
			// 生成短内容介绍，放在memo里面
			indexBean.setMemo(TextbookIndexUtil.getPartIntroduction(indexBean.getIntroduction()));
		}

		// 取出出版社的名字
		PressInfoBusiness pressBusiness = new PressInfoBusiness();
		PressInfoBean pressBean = new PressInfoBean();
		if (indexBean != null && !"".equals(indexBean.getPress())) {
			pressBean = pressBusiness.getPressInfoByKey(Integer.parseInt(indexBean.getPress()));
		}

		FreemarkerKit freemarkerKit = new FreemarkerKit();
		HashMap<String, Object> contentMap = freemarkerKit.getContentMap();
		CatalogInfoBusiness catalogInfoBusiness = new CatalogInfoBusiness();
		CatalogInfoBean catalogInfoBean = catalogInfoBusiness.getCatalogInfoByKey(indexBean.getCatalogId());
		contentMap.put("CatalogName", catalogInfoBean.getCatalogName());
		contentMap.put("TextbookInfo", indexBean);
		contentMap.put("TextbookClass", textbookClass);
		contentMap.put("CoverPath", TextbookPortalConfig.COVER_PATH);
		contentMap.put("Press", pressBean.getPressName());

		// 省份
		RegionServiceClient client = new RegionServiceClient();
		List<ProvinceInfoBean> provinceList = client.getProvinceInfoListByCountryCode("086");
		contentMap.put("provinceList", provinceList);
		// 取出出版社的名字
		List<PressInfoBean> pressList = pressBusiness.getPressInfoListAll();
		contentMap.put("pressList", pressList);
		contentMap.put("TextbookClassList", TextbookInfoConfig.TXT_CLASS_LIST);

		freemarkerKit.outputContent(contentMap, templateContentBean.getContent(), response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
