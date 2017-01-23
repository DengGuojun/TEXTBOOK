package com.lpmas.textbook.console.catalog.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.constant.language.LanguageConfig;
import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.template.bean.TemplateInfoBean;
import com.lpmas.template.client.TemplateServiceClient;
import com.lpmas.textbook.catalog.bean.CatalogInfoBean;
import com.lpmas.textbook.catalog.bean.CatalogTemplateBean;
import com.lpmas.textbook.catalog.config.CatalogConfig;
import com.lpmas.textbook.console.catalog.business.CatalogInfoBusiness;
import com.lpmas.textbook.console.catalog.business.CatalogTemplateBusiness;
import com.lpmas.textbook.console.catalog.config.CatalogConsoleConfig;
import com.lpmas.textbook.console.catalog.config.CatalogConsoleResource;

/**
 * Servlet implementation class CatalogInfoManage
 */
@WebServlet("/catalog/CatalogTemplateManage.do")
public class CatalogTemplateManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(CatalogTemplateManage.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);

		if (!(adminHelper.checkPermission(CatalogConsoleResource.CATALOG_TEMPLATE, OperationConfig.UPDATE)
				|| adminHelper.checkPermission(CatalogConsoleResource.CATALOG_TEMPLATE, OperationConfig.CREATE))) {
			return;
		}

		int catalogId = ParamKit.getIntParameter(request, "catalogId", 0);
		String language = ParamKit.getParameter(request, "language", LanguageConfig.LANG_CN);

		Map<String, Integer> catalogTemplateMap = new HashMap<String, Integer>();
		Map<String, String> templateNameMap = new HashMap<String, String>();
		if (catalogId > 0) {
			// 销售目录信息
			CatalogInfoBusiness catalogInfoBusiness = new CatalogInfoBusiness();
			CatalogInfoBean catalogInfoBean = catalogInfoBusiness.getCatalogInfoByKey(catalogId);
			request.setAttribute("CatalogInfo", catalogInfoBean);

			CatalogTemplateBusiness business = new CatalogTemplateBusiness();

			List<CatalogTemplateBean> catalogTemplateList = business.getCatalogTemplateListByKey(catalogId, language);
			for (CatalogTemplateBean bean : catalogTemplateList) {
				catalogTemplateMap.put(bean.getTemplateType(), bean.getTemplateId());

				if (bean.getTemplateId() > 0) {
					TemplateServiceClient templateServiceClient = new TemplateServiceClient();
					TemplateInfoBean templateInfoBean = templateServiceClient
							.getTemplateInfoByKey(bean.getTemplateId());
					templateNameMap.put(bean.getTemplateType(), templateInfoBean.getTemplateName());
				}
			}
		}

		request.setAttribute("CatalogTemplateListMap", catalogTemplateMap);
		request.setAttribute("TemplateNameMap", templateNameMap);
		log.info("list" + catalogTemplateMap.toString());
		request.setAttribute("AdminUserHelper", adminHelper);
		RequestDispatcher rd = request
				.getRequestDispatcher(CatalogConsoleConfig.PAGE_PATH + "CatalogTemplateManage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);
		if (!adminHelper.checkPermission(CatalogConsoleResource.CATALOG_TEMPLATE, OperationConfig.CREATE)
				|| !adminHelper.checkPermission(CatalogConsoleResource.CATALOG_TEMPLATE, OperationConfig.UPDATE)) {
			return;
		}

		int catalogId = ParamKit.getIntParameter(request, "catalogId", 0);
		String language = ParamKit.getParameter(request, "language", LanguageConfig.LANG_CN);
		CatalogTemplateBusiness business = new CatalogTemplateBusiness();

		int result = 0;
		for (StatusBean<String, String> templateBean : CatalogConfig.TEMPLATE_TYPE_LIST) {
			CatalogTemplateBean bean = new CatalogTemplateBean();
			int templateId = ParamKit.getIntParameter(request, templateBean.getStatus() + "_templateId", 0);
			String templateType = ParamKit.getParameter(request, templateBean.getStatus() + "_templateType");
			bean.setCatalogId(catalogId);
			bean.setTemplateType(templateType);
			bean.setLanguage(language);
			bean.setTemplateId(templateId);
			bean.setStatus(Constants.STATUS_VALID);// 默认有效

			CatalogTemplateBean originalBean = business.getCatalogTemplateByKey(catalogId, language, templateType);
			if (originalBean != null) {
				if (originalBean.getTemplateId() == bean.getTemplateId()) {
					continue;
				}
				bean.setModifyUser(adminHelper.getAdminUserId());
				result = business.updateCatalogTemplate(bean);
			} else {
				if (templateId == 0) {
					continue;
				}
				bean.setCreateUser(adminHelper.getAdminUserId());
				result = business.addCatalogTemplate(bean);
			}
			if (result < 0) {
				break;
			}
		}

		if (result >= 0) {// result = 0数据无更新则默认成功
			HttpResponseKit.alertMessage(response, "处理成功",
					"/catalog/CatalogTemplateManage.do?catalogId=" + catalogId + "&language=" + language);
		} else {
			HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
		}
	}

}
