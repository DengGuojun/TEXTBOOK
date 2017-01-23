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
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.region.bean.ProvinceInfoBean;
import com.lpmas.region.client.RegionServiceClient;
import com.lpmas.textbook.catalog.bean.CatalogInfoBean;
import com.lpmas.textbook.console.catalog.business.CatalogInfoBusiness;
import com.lpmas.textbook.console.catalog.config.CatalogConsoleConfig;
import com.lpmas.textbook.console.config.TextbookConsoleConfig;
import com.lpmas.textbook.console.textbook.business.PressInfoBusiness;
import com.lpmas.textbook.console.textbook.business.TextbookIndexBusiness;
import com.lpmas.textbook.console.textbook.config.TextbookConsoleTextbookConfig;
import com.lpmas.textbook.console.textbook.config.TextbookResource;
import com.lpmas.textbook.textbook.bean.PressInfoBean;
import com.lpmas.textbook.textbook.bean.TextbookIndexBean;

@WebServlet("/textbook/TextbookInfoList.do")
public class TextbookInfoList extends HttpServlet {
	public static final long serialVersionUID = 1L;

	public TextbookInfoList() {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_INFO, OperationConfig.SEARCH)) {
			HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		// 出版社
		List<PressInfoBean> pressList = new ArrayList<PressInfoBean>();
		PressInfoBusiness pressBusiness = new PressInfoBusiness();
		pressList = pressBusiness.getPressInfoListAll();
		request.setAttribute("pressInfoList", pressList);
		// 省份
		RegionServiceClient client = new RegionServiceClient();
		List<ProvinceInfoBean> provinceList = client
				.getProvinceInfoListByCountryCode(TextbookConsoleTextbookConfig.CHN_NUM);
		request.setAttribute("provinceList", provinceList);

		int pageNum = ParamKit.getIntParameter(request, "pageNum", TextbookConsoleConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", TextbookConsoleConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		HashMap<String, String> condMap = ParamKit.getParameterMap(request,
				"textbookName,textbookClass,year,press,province", "*");

		List<TextbookIndexBean> txtList = new ArrayList<TextbookIndexBean>();

		TextbookIndexBusiness textbookIndexBusiness = new TextbookIndexBusiness();
		PageResultBean<TextbookIndexBean> result = textbookIndexBusiness.getTextbookIndexPageListResult(condMap,
				pageBean);
		txtList = result.getRecordList();

		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());

		// 第一级的专业list`
		CatalogInfoBusiness catalogBusiness = new CatalogInfoBusiness();
		List<CatalogInfoBean> catalogList = new ArrayList<CatalogInfoBean>();
		catalogList = catalogBusiness.getCatalogListByParentId(CatalogConsoleConfig.ROOT_PARENT_ID);

		request.setAttribute("textbookInfoList", txtList);
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("AdminUserHelper", adminUserHelper);
		request.setAttribute("catalogInfoList", catalogList);
		request.setAttribute("txtClass", condMap.get("textbookClass"));
		RequestDispatcher rd = request
				.getRequestDispatcher(TextbookConsoleTextbookConfig.PAGE_PATH + "TextbookInfoList.jsp");
		rd.forward(request, response);
	}
}
