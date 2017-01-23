package com.lpmas.textbook.console.textbook.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.textbook.console.textbook.business.MediaInfoBusiness;
import com.lpmas.textbook.textbook.bean.MediaInfoBean;

@WebServlet("/textbook/TextbookMaterialStatusModify.do")
public class TextbookMaterialStatusModify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TextbookMaterialStatusModify() {
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
		String imgPath = ParamKit.getParameter(request, "imgPath", "");
		imgPath = imgPath.substring(imgPath.lastIndexOf("/") + 1);
		MediaInfoBean bean = new MediaInfoBean();

		MediaInfoBusiness mediaBusiness = new MediaInfoBusiness();
		bean = mediaBusiness.getMediaInfoByPath(imgPath);

		bean.setStatus(Constants.STATUS_NOT_VALID);
		mediaBusiness.updateMediaInfo(bean);
	}
}
