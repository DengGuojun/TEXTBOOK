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
import com.lpmas.textbook.console.textbook.business.PressInfoBusiness;
import com.lpmas.textbook.console.textbook.config.TextbookConsoleTextbookConfig;
import com.lpmas.textbook.console.textbook.config.TextbookResource;
import com.lpmas.textbook.textbook.bean.PressInfoBean;

@WebServlet("/textbook/PressInfoManage.do")
public class PressInfoManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PressInfoManage() {

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request);
		int pressId = ParamKit.getIntParameter(request, "pressId", 0);
		PressInfoBean bean = new PressInfoBean();
		if (pressId > 0) {
			if (!adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_PRESS, OperationConfig.UPDATE)) {
				HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			PressInfoBusiness business = new PressInfoBusiness();
			bean = business.getPressInfoByKey(pressId);
		} else {
			if (!adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_PRESS, OperationConfig.CREATE)) {
				HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
		}
		request.setAttribute("PressInfo", bean);
		request.setAttribute("AdminUserHelper", adminUserHelper);

		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(TextbookConsoleTextbookConfig.PAGE_PATH + "PressInfoManage.jsp");
		rd.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		PressInfoBean bean = new PressInfoBean();
		try {
			bean = BeanKit.request2Bean(request, PressInfoBean.class);
			PressInfoBusiness business = new PressInfoBusiness();
			ReturnMessageBean messageBean = new ReturnMessageBean();
			int result = 0;

			// 删除操作
			if (bean.getPressId() > 0 && bean.getStatus() == 0) {
				if (!adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_PRESS, OperationConfig.REMOVE)) {
					HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				// 验证是否被引用
				messageBean = business.isReferenced(bean);
				if (StringKit.isValid(messageBean.getMessage())) {
					HttpResponseKit.alertMessage(response, messageBean.getMessage(),
							HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				bean.setModifyUser(adminUserHelper.getAdminUserId());
				result = business.removePressInfo(bean);
			} else {
				messageBean = business.verifyPressInfo(bean);
				if (StringKit.isValid(messageBean.getMessage())) {
					HttpResponseKit.alertMessage(response, messageBean.getMessage(),
							HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}

				if (bean.getPressId() > 0) {
					if (!adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_PRESS, OperationConfig.UPDATE)) {
						HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
						return;
					}
					bean.setModifyUser(adminUserHelper.getAdminUserId());
					result = business.updatePressInfo(bean);

				} else {
					if (!adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_PRESS, OperationConfig.CREATE)) {
						HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
						return;
					}
					bean.setModifyUser(adminUserHelper.getAdminUserId());
					result = business.addPressInfo(bean);
				}
			}
			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/textbook/PressInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
		}

	}

}
