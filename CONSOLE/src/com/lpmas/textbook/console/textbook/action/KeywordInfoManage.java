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
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.textbook.console.textbook.business.KeywordInfoBusiness;
import com.lpmas.textbook.console.textbook.config.TextbookConsoleTextbookConfig;
import com.lpmas.textbook.console.textbook.config.TextbookResource;
import com.lpmas.textbook.textbook.bean.KeywordInfoBean;

@WebServlet("/textbook/KeywordInfoManage.do")
public class KeywordInfoManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public KeywordInfoManage() {

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request);
		KeywordInfoBean bean = new KeywordInfoBean();
		int keywordId = ParamKit.getIntParameter(request, "keywordId", 0);

		if (keywordId > 0) {
			if (!adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_KEYWORD, OperationConfig.UPDATE)) {
				HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			KeywordInfoBusiness business = new KeywordInfoBusiness();
			bean = business.getKeywordInfoByKey(keywordId);
		} else {
			if (!adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_KEYWORD, OperationConfig.CREATE)) {
				HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
		}
		request.setAttribute("keywordInfo", bean);
		request.setAttribute("AdminUserHelper", adminUserHelper);

		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(TextbookConsoleTextbookConfig.PAGE_PATH + "KeywordInfoManage.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		KeywordInfoBean bean = new KeywordInfoBean();
		try {
			bean = BeanKit.request2Bean(request, KeywordInfoBean.class);
			KeywordInfoBusiness business = new KeywordInfoBusiness();
			ReturnMessageBean messageBean = new ReturnMessageBean();
			int result = 0;

			// 删除操作
			if (bean.getKeywordId() > 0 && bean.getStatus() == 0) {
				if (!adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_KEYWORD, OperationConfig.REMOVE)) {
					HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				bean.setModifyUser(adminUserHelper.getAdminUserId());
				result = business.removeKeywordInfo(bean);
			} else {
				messageBean = business.verifyKeywordInfo(bean);
				if (StringKit.isValid(messageBean.getMessage())) {
					HttpResponseKit.alertMessage(response, messageBean.getMessage(),
							HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}

				if (bean.getKeywordId() > 0) {
					if (!adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_KEYWORD, OperationConfig.UPDATE)) {
						HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
						return;
					}
					bean.setModifyUser(adminUserHelper.getAdminUserId());
					result = business.updateKeywordInfo(bean);

				} else {
					if (!adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_KEYWORD, OperationConfig.CREATE)) {
						HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
						return;
					}
					bean.setModifyUser(adminUserHelper.getAdminUserId());
					result = business.addKeywordInfo(bean);
				}
			}
			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/textbook/KeywordInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
		}

	}
}
