package com.lpmas.textbook.console.textbook.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.textbook.console.textbook.config.TextbookConsoleTextbookConfig;
import com.lpmas.textbook.console.textbook.config.TextbookResource;

@WebServlet("/textbook/TextbookSelectClass.do")
public class TextbookSelectClass extends HttpServlet {
	public static final long serialVersionUID = 1L;

	public TextbookSelectClass() {
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
		AdminUserHelper adminUserHelper = new AdminUserHelper(request);

		if (!adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_INFO, OperationConfig.SEARCH)) {
			HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		RequestDispatcher rd = request
				.getRequestDispatcher(TextbookConsoleTextbookConfig.PAGE_PATH + "TextbookSelectClass.jsp");
		rd.forward(request, response);
	}
}
