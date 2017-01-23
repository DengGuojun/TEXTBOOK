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
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.textbook.console.textbook.business.ArticleInfoBusiness;
import com.lpmas.textbook.console.textbook.config.TextbookConsoleTextbookConfig;
import com.lpmas.textbook.console.textbook.config.TextbookResource;
import com.lpmas.textbook.textbook.bean.ArticleInfoBean;

@WebServlet("/textbook/ArticleInfoManage.do")
public class ArticleInfoManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ArticleInfoManage() {

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request);
		int articleId = ParamKit.getIntParameter(request, "ArticleId", 0);
		ArticleInfoBean bean = new ArticleInfoBean();
		ArticleInfoBusiness business = new ArticleInfoBusiness();
		int result = 0;

		if (articleId > 0) {
			// 删除操作
			if (ParamKit.getIntParameter(request, "DELETE", 0) == 1) {
				if (!adminUserHelper.checkPermission(TextbookResource.ARTICLE_INFO, OperationConfig.REMOVE)) {
					HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				bean.setArticleId(articleId);
				bean.setModifyUser(adminUserHelper.getAdminUserId());
				result = business.removeArticleInfo(bean);

				if (result > 0) {
					HttpResponseKit.alertMessage(response, "处理成功", "/textbook/ArticleInfoList.do");
				} else {
					HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
				}
				return;
			}

			if (!adminUserHelper.checkPermission(TextbookResource.ARTICLE_INFO, OperationConfig.UPDATE)) {
				HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			bean = business.getArticleInfoByKey(articleId);
		} else {
			if (!adminUserHelper.checkPermission(TextbookResource.ARTICLE_INFO, OperationConfig.CREATE)) {
				HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
		}
		request.setAttribute("ArticleInfo", bean);
		request.setAttribute("AdminUserHelper", adminUserHelper);

		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(TextbookConsoleTextbookConfig.PAGE_PATH + "ArticleInfoManage.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		ArticleInfoBean bean = new ArticleInfoBean();
		try {
			bean = BeanKit.request2Bean(request, ArticleInfoBean.class);
			ArticleInfoBusiness business = new ArticleInfoBusiness();
			int result = 0;

			if (bean.getArticleId() > 0) {
				if (!adminUserHelper.checkPermission(TextbookResource.ARTICLE_INFO, OperationConfig.UPDATE)) {
					HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				bean.setModifyUser(adminUserHelper.getAdminUserId());
				result = business.updateArticleInfo(bean);

			} else {
				if (!adminUserHelper.checkPermission(TextbookResource.ARTICLE_INFO, OperationConfig.CREATE)) {
					HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				bean.setCreateUser(adminUserHelper.getAdminUserId());
				result = business.addArticleInfo(bean);
			}
			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/textbook/ArticleInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
