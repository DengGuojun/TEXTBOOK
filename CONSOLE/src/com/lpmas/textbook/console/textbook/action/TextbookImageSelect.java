package com.lpmas.textbook.console.textbook.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.textbook.console.config.TextbookConsoleConfig;
import com.lpmas.textbook.console.textbook.business.MediaInfoBusiness;
import com.lpmas.textbook.textbook.bean.MediaInfoBean;

@WebServlet("/textbook/TextbookImageSelect.do")
public class TextbookImageSelect extends HttpServlet {
	public static final long serialVersionUID = 1L;

	public TextbookImageSelect() {
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
		// AdminUserHelper adminUserHelper = new AdminUserHelper(request);

		MediaInfoBusiness business = new MediaInfoBusiness();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", Constants.STATUS_VALID + "");
		ReturnMessageBean returnMessage = new ReturnMessageBean();

		int pageNum = ParamKit.getIntParameter(request, "pageNum", TextbookConsoleConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", TextbookConsoleConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		PageResultBean<MediaInfoBean> list = business.getMediaInfoPageListByMap(condMap, pageBean);
		List<String> resultList = new ArrayList<String>();

		for (MediaInfoBean bean : list.getRecordList()) {
			resultList.add(TextbookConsoleConfig.RELATIVE_MATERIAL_PATH + bean.getMedialPath());
		}
		pageBean.init(pageNum, pageSize, list.getTotalRecordNumber());
		returnMessage.setMessage(JsonKit.toJson(resultList));
		returnMessage.setContent(pageBean);
		HttpResponseKit.printJson(request, response, returnMessage, "");
	}
}
