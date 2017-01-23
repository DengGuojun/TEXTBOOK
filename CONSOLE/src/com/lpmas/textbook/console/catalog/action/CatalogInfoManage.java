package com.lpmas.textbook.console.catalog.action;

import java.io.IOException;
import java.util.HashMap;

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
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.textbook.catalog.bean.CatalogInfoBean;
import com.lpmas.textbook.console.catalog.business.CatalogInfoBusiness;
import com.lpmas.textbook.console.catalog.business.CatalogInfoUtil;
import com.lpmas.textbook.console.catalog.config.CatalogConsoleConfig;
import com.lpmas.textbook.console.catalog.config.CatalogConsoleResource;

@WebServlet("/catalog/CatalogInfoManage.do")
public class CatalogInfoManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(CatalogInfoManage.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		int catalogId = ParamKit.getIntParameter(request, "catalogId", 0);
		int parentCatalogId = ParamKit.getIntParameter(request, "parentCatalogId", 1);
		CatalogInfoBean bean = new CatalogInfoBean();
		CatalogInfoBusiness business = new CatalogInfoBusiness();
		CatalogInfoBean firstParentBean = new CatalogInfoBean();
		CatalogInfoBean secondParentBean = new CatalogInfoBean();

		HashMap<String, CatalogInfoBean> parentMap = new HashMap<String, CatalogInfoBean>();
		if (catalogId > CatalogConsoleConfig.ROOT_PARENT_ID) {
			if (!adminUserHelper.checkPermission(CatalogConsoleResource.CATALOG_INFO, OperationConfig.UPDATE)) {
				return;
			}
			bean = business.getCatalogInfoByKey(catalogId);

		} else {
			if (!adminUserHelper.checkPermission(CatalogConsoleResource.CATALOG_INFO, OperationConfig.CREATE)) {
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
			bean.setParentCatalogId(parentCatalogId);
		}
		parentMap = CatalogInfoUtil.getParentProgramType(bean);
		firstParentBean = parentMap.get("firstParentBean");
		secondParentBean = parentMap.get("secondParentBean");

		request.setAttribute("catalogInfo", bean);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		request.setAttribute("firstParentBean", firstParentBean);
		request.setAttribute("secondParentBean", secondParentBean);

		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(CatalogConsoleConfig.PAGE_PATH + "CatalogInfoManage.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		ReturnMessageBean messageBean = new ReturnMessageBean();
		CatalogInfoBean bean = new CatalogInfoBean();

		try {
			bean = BeanKit.request2Bean(request, CatalogInfoBean.class);
			CatalogInfoBusiness business = new CatalogInfoBusiness();
			int result = 0;

			// 删除
			if (bean.getCatalogId() > 0 && bean.getStatus() == 0) {
				if (!adminUserHelper.checkPermission(CatalogConsoleResource.CATALOG_INFO, OperationConfig.REMOVE)) {
					return;
				}
				// 检查是否有子类
				messageBean = business.isHaveChild(bean);
				if (StringKit.isValid(messageBean.getMessage())) {
					HttpResponseKit.alertMessage(response, messageBean.getMessage(),
							HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				// 是否被引用，待补充
				messageBean = business.isReferenced(bean);
				if (StringKit.isValid(messageBean.getMessage())) {
					HttpResponseKit.alertMessage(response, messageBean.getMessage(),
							HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}

				bean.setModifyUser(adminUserHelper.getAdminUserId());
				result = business.removeCatalogInfo(bean);
			} else {
				messageBean = business.verifyCatalogInfo(bean);
				if (StringKit.isValid(messageBean.getMessage())) {
					HttpResponseKit.alertMessage(response, messageBean.getMessage(),
							HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				if (bean.getCatalogId() > 0) {
					if (!adminUserHelper.checkPermission(CatalogConsoleResource.CATALOG_INFO, OperationConfig.UPDATE)) {
						return;
					}
					bean.setModifyUser(adminUserHelper.getAdminUserId());
					result = business.updateCatalogInfo(bean);
				} else {
					if (!adminUserHelper.checkPermission(CatalogConsoleResource.CATALOG_INFO, OperationConfig.CREATE)) {
						return;
					}
					bean.setModifyUser(adminUserHelper.getAdminUserId());
					result = business.addCatalogInfo(bean);
				}
			}
			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功",
						"/catalog/CatalogInfoList.do?catalogId=" + bean.getParentCatalogId());
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}
}
