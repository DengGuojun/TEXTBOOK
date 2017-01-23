package com.lpmas.textbook.textbook.bean;

import java.sql.Timestamp;
import com.lpmas.framework.annotation.FieldTag;

public class TextbookMediaBean {
	@FieldTag(name = "图书ID")
	private int textbookId = 0;
	@FieldTag(name = "图片类型")
	private String mediaType = "";
	@FieldTag(name = "多媒体类型")
	private String fileType = "";
	@FieldTag(name = "链接")
	private String mediaUrl = "";
	@FieldTag(name = "创建时间")
	private Timestamp createTime = null;
	@FieldTag(name = "创建用户")
	private int createUser = 0;
	@FieldTag(name = "修改时间")
	private Timestamp modifyTime = null;
	@FieldTag(name = "修改用户")
	private int modifyUser = 0;

	public int getTextbookId() {
		return textbookId;
	}

	public void setTextbookId(int textbookId) {
		this.textbookId = textbookId;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getCreateUser() {
		return createUser;
	}

	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(int modifyUser) {
		this.modifyUser = modifyUser;
	}
}