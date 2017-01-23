package com.lpmas.textbook.console.textbook.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.textbook.console.textbook.business.TextbookDescriptionBusiness;
import com.lpmas.textbook.console.textbook.business.TextbookInfoBusiness;
import com.lpmas.textbook.console.textbook.config.TextbookConsoleTextbookConfig;
import com.lpmas.textbook.console.textbook.config.TextbookResource;
import com.lpmas.textbook.textbook.bean.TextbookDescriptionBean;
import com.lpmas.textbook.textbook.bean.TextbookInfoBean;
import com.lpmas.textbook.textbook.config.TextbookInfoConfig;

@WebServlet("/textbook/TextbookInfoStatusModify.do")
public class TextbookInfoStatusModify extends HttpServlet {
	public static final long serialVersionUID = 1L;
	// private static Logger log =
	// LoggerFactory.getLogger(TextbookInfoStatusModify.class);

	public TextbookInfoStatusModify() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(TextbookResource.TEXTBOOK_INFO, OperationConfig.UPDATE)) {
			HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		TextbookInfoBusiness infoBusiness = new TextbookInfoBusiness();
		TextbookInfoBean infoBean = new TextbookInfoBean();
		//
		String operationIdStr = ParamKit.getParameter(request, "waitForOutId", "");
		String[] id = null;
		if (StringKit.isValid(operationIdStr)) {
			id = operationIdStr.split("-");
		}

		// 操作类型
		int actionType = ParamKit.getIntParameter(request, "actionType", 0);

		List<String> outSellList = new ArrayList<String>();// 下架成功的书名称
		if (id.length > 0) {
			if (actionType == TextbookConsoleTextbookConfig.OUT_SELL) {// 下架

				for (int i = 0; i < id.length; i++) {
					infoBean = infoBusiness.getTextbookInfoByKey(Integer.parseInt(id[i]));

					if (TextbookInfoConfig.SELLING_STATUS_ON.equals(infoBean.getSellingStatus())) {
						infoBean.setSellingStatus(TextbookInfoConfig.SELLING_STATUS_OFF);
						infoBean.setModifyUser(adminUserHelper.getAdminUserId());
						infoBusiness.offSellTextbook(infoBean);

						outSellList.add(infoBean.getTextbookName());
					}
				}

				HttpResponseKit.printJson(request, response, outSellList, "");

			} else if (actionType == TextbookConsoleTextbookConfig.ON_SELL) {// 上架
				for (int i = 0; i < id.length; i++) {
					infoBean = infoBusiness.getTextbookInfoByKey(Integer.parseInt(id[i]));

					if (TextbookInfoConfig.SELLING_STATUS_OFF.equals(infoBean.getSellingStatus())) {
						infoBean.setSellingStatus(TextbookInfoConfig.SELLING_STATUS_ON);
						infoBean.setModifyUser(adminUserHelper.getAdminUserId());
						infoBusiness.onSellTextbook(infoBean);

						outSellList.add(infoBean.getTextbookName());
					}
				}

				HttpResponseKit.printJson(request, response, outSellList, "");
			} else if (actionType == TextbookConsoleTextbookConfig.SELECT_TEXTBOOKCLASS) { // 更改类型

				String textbookClass = ParamKit.getParameter(request, "textbookClass", "");

				TextbookDescriptionBean descBean = new TextbookDescriptionBean();
				TextbookDescriptionBusiness descBusiness = new TextbookDescriptionBusiness();
				for (int i = 0; i < id.length; i++) {
					descBean = descBusiness.getTextbookDescriptionByKey(Integer.parseInt(id[i]),
							TextbookInfoConfig.TXT_DESC_TEXTBOOK_CLASS);
					if (!descBean.getDescValue().equals(textbookClass)) {// 不等的时候就进行更改
						descBean.setDescValue(textbookClass);
						descBusiness.updateTextbookDescription(descBean);

						outSellList.add(infoBusiness.getTextbookInfoByKey(Integer.parseInt(id[i])).getTextbookName());
					}
				}
				HttpResponseKit.printJson(request, response, outSellList, "");
			}

		}

	}
}
