package com.lpmas.textbook.console.catalog.action;

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
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.textbook.catalog.bean.CatalogInfoBean;
import com.lpmas.textbook.console.catalog.business.CatalogInfoBusiness;
import com.lpmas.textbook.console.catalog.config.CatalogConsoleConfig;
import com.lpmas.textbook.console.catalog.config.CatalogConsoleResource;
import com.lpmas.textbook.console.config.TextbookConsoleConfig;

@WebServlet("/catalog/CatalogInfoList.do")
public class CatalogInfoList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		AdminUserHelper adminUserHelper = new AdminUserHelper(req, res);
		if (!adminUserHelper.checkPermission(CatalogConsoleResource.CATALOG_INFO, OperationConfig.SEARCH)) {
			return;
		}
		int catalogId = ParamKit.getIntParameter(req, "catalogId", 0);
		List<CatalogInfoBean> navigationBar = new ArrayList<CatalogInfoBean>();
		HashMap<Integer, String> parentNameMap = new HashMap<Integer, String>();

		int pageNum = ParamKit.getIntParameter(req, "pageNum", TextbookConsoleConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(req, "pageSize", TextbookConsoleConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		HashMap<String, String> condMap = ParamKit.getParameterMap(req, "catalogName,parentCatalogId", "");
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		condMap.put("orderBy", "priority");

		CatalogInfoBusiness business = new CatalogInfoBusiness();

		if (!StringKit.isValid(condMap.get("catalogName"))) {
			// 默认为搜根目录
			if (catalogId > 0) {
				condMap.put("parentCatalogId", catalogId + "");
			} else {
				condMap.put("parentCatalogId", CatalogConsoleConfig.ROOT_PARENT_ID + "");

			}
		}

		PageResultBean<CatalogInfoBean> result = business.getCatalogInfoPageListByMap(condMap, pageBean);

		// 生成父类名
		parentNameMap.put(CatalogConsoleConfig.ROOT_PARENT_ID, CatalogConsoleConfig.ROOT_PARENT_NAME);
		if (!result.getRecordList().isEmpty()) {
			for (CatalogInfoBean bean : result.getRecordList()) {
				if (!parentNameMap.containsKey(bean.getParentCatalogId())) {
					parentNameMap.put(bean.getParentCatalogId(),
							business.getCatalogInfoByKey(bean.getParentCatalogId()).getCatalogName());
				}
			}
		}

		// 生成导航栏
		if (catalogId > CatalogConsoleConfig.ROOT_PARENT_ID) {
			CatalogInfoBean firstBean = business.getCatalogInfoByKey(catalogId);
			CatalogInfoBean secondBean = null;
			if (firstBean.getParentCatalogId() != CatalogConsoleConfig.ROOT_PARENT_ID) {
				secondBean = business.getCatalogInfoByKey(firstBean.getParentCatalogId());
			}
			if (secondBean != null) {
				navigationBar.add(secondBean);
			}
			navigationBar.add(firstBean);
		}

		req.setAttribute("parentCatalogId", catalogId);
		req.setAttribute("CatalogInfoList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		req.setAttribute("PageResult", pageBean);
		req.setAttribute("CondList", MapKit.map2List(condMap));
		req.setAttribute("AdminUserHelper", adminUserHelper);

		req.setAttribute("parentNameMap", parentNameMap);
		req.setAttribute("navigationBar", navigationBar);
		RequestDispatcher rd = req.getRequestDispatcher(CatalogConsoleConfig.PAGE_PATH + "CatalogInfoList.jsp");
		rd.forward(req, res);

	}
}
