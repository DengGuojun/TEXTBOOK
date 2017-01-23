package com.lpmas.textbook.console.textbook.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.textbook.console.textbook.business.RotationInfoBusiness;
import com.lpmas.textbook.console.textbook.config.TextbookConsoleTextbookConfig;
import com.lpmas.textbook.console.textbook.config.TextbookResource;
import com.lpmas.textbook.textbook.bean.RotationInfoBean;

@WebServlet("/textbook/RotationInfoManage.do")
public class RotationInfoManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(RotationInfoManage.class);

	public RotationInfoManage() {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(TextbookResource.ROTATION_INFO, OperationConfig.SEARCH)) {
			HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		HashMap<String, String> condMap = new HashMap<>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));

		RotationInfoBusiness business = new RotationInfoBusiness();
		List<RotationInfoBean> rotationInfoList = business.getRotationInfoListByMap(condMap);

		request.setAttribute("RotationInfoList", rotationInfoList);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("AdminUserHelper", adminUserHelper);
		RequestDispatcher rd = request
				.getRequestDispatcher(TextbookConsoleTextbookConfig.PAGE_PATH + "RotationInfoManage.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		RotationInfoBusiness business = new RotationInfoBusiness();
		int result = 0;

		if (!adminUserHelper.checkPermission(TextbookResource.ROTATION_INFO, OperationConfig.UPDATE)) {
			HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		HashMap<String, String> condMap = new HashMap<>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));

		for (int i = 0; i < 5; i++) {
			String imageUrl = ParamKit.getParameter(request, "imageUrl" + i);
			log.info(imageUrl);
			if (StringKit.isValid(imageUrl)) {
				RotationInfoBean bean = new RotationInfoBean();

				int rotationId = ParamKit.getIntParameter(request, "rotationId" + i, 0);
				String imageTitle = ParamKit.getParameter(request, "imageTitle" + i);
				String originalUrl = ParamKit.getParameter(request, "originalUrl" + i);
				int priority = ParamKit.getIntParameter(request, "priority" + i, 0);
				if (rotationId > 0) {
					bean = business.getRotationInfoByKey(rotationId);
					if (!imageUrl.equals(bean.getImageUrl())) {
						// 当图片路径不同时更新数据库
						bean.setImageUrl(imageUrl);
						bean.setImageTitle(imageTitle);
						bean.setOriginalUrl(originalUrl);
						bean.setPriority(priority);
					} else if (!imageTitle.equals(bean.getImageTitle()) || !originalUrl.equals(bean.getOriginalUrl())
							|| priority != bean.getPriority()) {
						bean.setImageTitle(imageTitle);
						bean.setOriginalUrl(originalUrl);
						bean.setPriority(priority);
					}
					bean.setModifyUser(adminUserHelper.getAdminUserId());
					result += business.updateRotationInfo(bean);
				} else {
					bean.setImageUrl(imageUrl);
					bean.setImageTitle(imageTitle);
					bean.setOriginalUrl(originalUrl);
					bean.setPriority(priority);
					bean.setStatus(Constants.STATUS_VALID);
					bean.setCreateUser(adminUserHelper.getAdminUserId());
					result += business.addRotationInfo(bean);
				}
			}
		}
		log.info("处理了 " + result + " 条数据");
		if (result > 0) {
			HttpResponseKit.alertMessage(response, "处理成功", "/textbook/RotationInfoManage.do");
		} else {
			HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
		}
	}
}
