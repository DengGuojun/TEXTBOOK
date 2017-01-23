package com.lpmas.textbook.portal.textbook.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.textbook.catalog.bean.CatalogInfoBean;
import com.lpmas.textbook.portal.catalog.business.CatalogInfoBusiness;

@WebServlet("/catalog/CatalogInfoSelect.do")
public class CatalogInfoSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int catalogId = ParamKit.getIntParameter(request, "catalogId", 0);// 取Id,做弗雷id
		CatalogInfoBusiness business = new CatalogInfoBusiness();
		List<CatalogInfoBean> list = business.getCatalogListByParentId(catalogId);
		if (list.size() == 0) {
			CatalogInfoBean bean = new CatalogInfoBean();
			list.add(bean);
		}
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		Map<String, List<CatalogInfoBean>> map = new HashMap<String, List<CatalogInfoBean>>();
		map.put("result", list);
		String result = JsonKit.toJson(map);
		writer.write(result);
		writer.flush();
		writer.close();
	}
}
