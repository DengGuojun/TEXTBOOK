package com.lpmas.textbook.console.textbook.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.transfer.FileUploadKit;
import com.lpmas.framework.transfer.FileUploadResultBean;
import com.lpmas.framework.transfer.FileUploadResultBean.FileUploadItem;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.util.UuidKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.textbook.console.config.TextbookConsoleConfig;
import com.lpmas.textbook.console.textbook.config.TextbookResource;

@WebServlet("/textbook/RotationMediaManage.do")
public class RotationMediaManage extends HttpServlet {
	public static final long serialVersionUID = 1L;

	public RotationMediaManage() {
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

		if (!adminUserHelper.checkPermission(TextbookResource.ROTATION_INFO, OperationConfig.CREATE)) {
			HttpResponseKit.alertMessage(response, "用户权限不足", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		Map<String, String> resultMap = new HashMap<String, String>();
		ReturnMessageBean returnMessage = new ReturnMessageBean();
		String uploadPath = TextbookConsoleConfig.ROTATION_PATH;

		try {
			FileUploadKit fileUploadKit = new FileUploadKit();
			String fileName = UuidKit.getUuid();
			fileUploadKit.setAllowedFileType(TextbookConsoleConfig.UPLOAD_ALLOWED_FILE_TYPE);

			FileUploadResultBean resultBean = fileUploadKit.fileUpload(request, "file", uploadPath, fileName);
			if (resultBean.getResult()) {

				List<FileUploadItem> list = resultBean.getFileItemList();
				for (FileUploadItem item : list) {
					if (item.getResult()) {
						resultMap.put("fileName", fileName + "." + item.getExtensionFileName());
						resultMap.put("pathName", TextbookConsoleConfig.RELATIVE_ROTATION_PATH + fileName + "."
								+ item.getExtensionFileName());
					} else {
						returnMessage.setCode(Constants.STATUS_NOT_VALID);
						returnMessage.setMessage(item.getResultContent());
						HttpResponseKit.printJson(request, response, returnMessage, "");
						return;
					}
				}
				returnMessage.setCode(Constants.STATUS_VALID);
				returnMessage.setMessage(JsonKit.toJson(resultMap));
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
