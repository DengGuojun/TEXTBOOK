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

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.transfer.FileUploadKit;
import com.lpmas.framework.transfer.FileUploadResultBean;
import com.lpmas.framework.transfer.FileUploadResultBean.FileUploadItem;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.util.UuidKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.textbook.console.config.TextbookConsoleConfig;
import com.lpmas.textbook.console.textbook.business.MediaInfoBusiness;
import com.lpmas.textbook.textbook.bean.MediaInfoBean;

@WebServlet("/textbook/TextbookMediaManage.do")
public class TextbookMediaManage extends HttpServlet {
	public static final long serialVersionUID = 1L;

	public TextbookMediaManage() {
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
		AdminUserHelper adminUserHelper = new AdminUserHelper(request);
		List<String> resultList = new ArrayList<String>();
		ReturnMessageBean returnMessage = new ReturnMessageBean();
		String uploadType = ParamKit.getParameter(request, "uploadType", "cover");
		String uploadPath = "";

		PageBean pageBean = new PageBean();
		try {
			FileUploadKit fileUploadKit = new FileUploadKit();
			String fileName = UuidKit.getUuid();
			fileUploadKit.setAllowedFileType(TextbookConsoleConfig.UPLOAD_ALLOWED_FILE_TYPE);

			if (TextbookConsoleConfig.UPLOAD_TYPE_MATERIAL.equals(uploadType)) {
				uploadPath = TextbookConsoleConfig.MATERIAL_PATH;

			} else if (TextbookConsoleConfig.UPLOAD_TYPE_COVER.equals(uploadType)) {
				uploadPath = TextbookConsoleConfig.COVER_PATH;
			}
			FileUploadResultBean resultBean = fileUploadKit.fileUpload(request, "file", uploadPath, fileName);
			if (resultBean.getResult()) {
				if (TextbookConsoleConfig.UPLOAD_TYPE_MATERIAL.equals(uploadType)) {// material存好后，要进行存储到mediaInfo
					MediaInfoBusiness mediaBusiness = new MediaInfoBusiness();
					List<FileUploadItem> list2 = resultBean.getFileItemList();

					for (FileUploadItem item : list2) {
						if (item.getResult()) {
							MediaInfoBean mediaBean = new MediaInfoBean();
							mediaBean.setMedialPath(fileName + "." + item.getExtensionFileName());
							mediaBean.setCreateUser(adminUserHelper.getAdminUserId());
							mediaBean.setStatus(Constants.STATUS_VALID);
							mediaBusiness.addMediaInfo(mediaBean);
						} else {
							returnMessage.setCode(Constants.STATUS_NOT_VALID);
							returnMessage.setMessage(item.getResultContent());
							HttpResponseKit.printJson(request, response, returnMessage, "");
							return;
						}
					}
					HashMap<String, String> condMap = new HashMap<String, String>();
					condMap.put("status", Constants.STATUS_VALID + "");
					PageResultBean<MediaInfoBean> mediaList = mediaBusiness.getMediaInfoPageListByMap(condMap,
							new PageBean(1, 20));
					for (MediaInfoBean tempBean : mediaList.getRecordList()) {
						resultList.add(TextbookConsoleConfig.RELATIVE_MATERIAL_PATH + tempBean.getMedialPath());
					}
					pageBean.init(1, 20, mediaList.getTotalRecordNumber());
				} else if (TextbookConsoleConfig.UPLOAD_TYPE_COVER.equals(uploadType)) {
					List<FileUploadItem> list = resultBean.getFileItemList();
					for (FileUploadItem item : list) {
						if (item.getResult()) {
							resultList.add(TextbookConsoleConfig.RELATIVE_COVER_PATH + fileName + "."
									+ item.getExtensionFileName());
						} else {
							returnMessage.setCode(Constants.STATUS_NOT_VALID);
							returnMessage.setMessage(item.getResultContent());
							HttpResponseKit.printJson(request, response, returnMessage, "");
							return;
						}
					}
				}
				if (TextbookConsoleConfig.UPLOAD_TYPE_MATERIAL.equals(uploadType)) {// 如果是物料的话返回的时候，要加一个pageBean
					returnMessage.setCode(Constants.STATUS_VALID);
					returnMessage.setMessage(JsonKit.toJson(resultList));
					returnMessage.setContent(pageBean);
					HttpResponseKit.printJson(request, response, returnMessage, "");
					return;
				}
				returnMessage.setCode(Constants.STATUS_VALID);
				returnMessage.setMessage(JsonKit.toJson(resultList));
				HttpResponseKit.printJson(request, response, returnMessage, "");
				return;
			} else {
				returnMessage.setCode(Constants.STATUS_NOT_VALID);
				returnMessage.setMessage(resultBean.getResultContent());
				HttpResponseKit.printJson(request, response, returnMessage, "");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
