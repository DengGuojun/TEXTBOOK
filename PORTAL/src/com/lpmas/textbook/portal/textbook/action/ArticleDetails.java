package com.lpmas.textbook.portal.textbook.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.textbook.portal.config.TextbookPortalConfig;
import com.lpmas.textbook.portal.textbook.business.ArticleInfoBusiness;
import com.lpmas.textbook.textbook.bean.ArticleInfoBean;

@WebServlet("/textbook/ArticleDetails.do")
public class ArticleDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ArticleDetails() {

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

		int articleId = ParamKit.getIntParameter(request, "ArticleId", 0);
		ArticleInfoBean bean = new ArticleInfoBean();
		ArticleInfoBusiness business = new ArticleInfoBusiness();

		if (articleId > 0) {
			bean = business.getArticleInfoByKey(articleId);
		} else {
			HttpResponseKit.alertMessage(response, "无此文章", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		request.setAttribute("ArticleInfo", bean);
		request.setAttribute("AdminUserHelper", adminUserHelper);

		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(TextbookPortalConfig.PAGE_PATH + "ArticleDetails.jsp");
		rd.forward(request, response);
	}
}
