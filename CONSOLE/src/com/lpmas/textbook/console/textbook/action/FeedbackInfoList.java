package com.lpmas.textbook.console.textbook.action;

import java.io.IOException;
import java.util.HashMap;

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
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.textbook.console.config.TextbookConsoleConfig;
import com.lpmas.textbook.console.textbook.business.FeedbackInfoBusiness;
import com.lpmas.textbook.console.textbook.config.TextbookConsoleTextbookConfig;
import com.lpmas.textbook.console.textbook.config.TextbookResource;
import com.lpmas.textbook.textbook.bean.FeedbackInfoBean;

@WebServlet("/textbook/FeedbackInfoList.do")
public class FeedbackInfoList extends HttpServlet {
	public static final long serialVersionUID = 1L;

	public FeedbackInfoList() {
		super();
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
		if (!adminUserHelper.checkPermission(TextbookResource.FEEDBACK_INFO, OperationConfig.SEARCH)) {
			HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		int pageNum = ParamKit.getIntParameter(request, "pageNum", TextbookConsoleConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", TextbookConsoleConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		HashMap<String, String> condMap = new HashMap<>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));

		FeedbackInfoBusiness business = new FeedbackInfoBusiness();
		PageResultBean<FeedbackInfoBean> result = business.getFeedbackInfoPageListByMap(condMap, pageBean);

		request.setAttribute("FeedbackInfoList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("AdminUserHelper", adminUserHelper);
		RequestDispatcher rd = request
				.getRequestDispatcher(TextbookConsoleTextbookConfig.PAGE_PATH + "FeedbackInfoList.jsp");
		rd.forward(request, response);
	}
}
