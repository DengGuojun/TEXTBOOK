package com.lpmas.textbook.portal.textbook.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.textbook.portal.config.TextbookPortalConfig;
import com.lpmas.textbook.portal.textbook.business.FeedbackInfoBusiness;
import com.lpmas.textbook.textbook.bean.FeedbackInfoBean;

@WebServlet("/textbook/FeedbackSubmit.do")
public class FeedbackSubmit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FeedbackSubmit() {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = TextbookPortalConfig.PAGE_PATH + "FeedbackSubmit.jsp";
		request.getRequestDispatcher(path).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		FeedbackInfoBean bean = new FeedbackInfoBean();
		try {
			bean = BeanKit.request2Bean(request, FeedbackInfoBean.class);
			FeedbackInfoBusiness business = new FeedbackInfoBusiness();
			int result = 0;

			if (!StringKit.isValid(bean.getTextbookName()) || !StringKit.isValid(bean.getContactInfo())) {
				HttpResponseKit.alertMessage(response, "请填写必要信息", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			bean.setCreateUser(adminUserHelper.getAdminUserId());
			result = business.addFeedbackInfo(bean);
			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", HttpResponseKit.ACTION_HISTORY_BACK);
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_NONE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
