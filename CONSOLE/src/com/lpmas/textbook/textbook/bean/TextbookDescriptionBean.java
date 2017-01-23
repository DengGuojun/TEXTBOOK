package com.lpmas.textbook.textbook.bean;

import java.sql.Timestamp;
import com.lpmas.framework.annotation.FieldTag;

public class TextbookDescriptionBean {
	@FieldTag(name = "图书ID")
	private int textbookId = 0;
	@FieldTag(name = "描述类型")
	private String descCode = "";
	@FieldTag(name = "描述内容")
	private String descValue = "";
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

	public String getDescCode() {
		return descCode;
	}

	public void setDescCode(String descCode) {
		this.descCode = descCode;
	}

	public String getDescValue() {
		return descValue;
	}

	public void setDescValue(String descValue) {
		this.descValue = descValue;
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