package com.lpmas.textbook.textbook.bean;

import org.apache.solr.client.solrj.beans.Field;

import com.lpmas.framework.index.solr.SolrDocumentBean;

public class TextbookIndexBean extends SolrDocumentBean {

	@Field
	private String textbookName = "";
	@Field
	private String price = "";
	@Field
	private String overClassification = "";
	@Field
	private String year = "";
	@Field
	private String province = "";
	@Field
	private String textbookClass = "";
	@Field
	private String textbookType = "";
	@Field
	private String groupEdit = "";
	@Field
	private String mainEdit = "";
	@Field
	private String guideTeacher = "";
	@Field
	private String press = "";
	@Field
	private String publicationDate = "";
	@Field
	private String bookFormat = "";
	@Field
	private String introduction = "";
	@Field
	private String writeDescription = "";
	@Field
	private String contents = "";
	@Field
	private String firstChapter = "";
	@Field
	private int catalogId = 0;
	@Field
	private String catalogName = "";
	@Field
	private String cover0 = "";
	@Field
	private String cover1 = "";
	@Field
	private String cover2 = "";
	@Field
	private String cover3 = "";
	@Field
	private String cover4 = "";
	@Field
	private String cover5 = "";
	@Field
	private String cover6 = "";
	@Field
	private String cover7 = "";
	@Field
	private String cover8 = "";
	@Field
	private String cover9 = "";
	@Field
	private String sellingStatus = "";
	@Field
	private int priority = 0;

	@Field
	private int createUser = 0;

	@Field
	private long createTime = 0L;

	@Field
	private int modifyUser = 0;

	@Field
	private long modifyTime = 0L;

	@Field
	private String memo = "";

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getWriteDescription() {
		return writeDescription;
	}

	public void setWriteDescription(String writeDescription) {
		this.writeDescription = writeDescription;
	}

	public String getTextbookName() {
		return textbookName;
	}

	public void setTextbookName(String textbookName) {
		this.textbookName = textbookName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getModifyUser() {
		return modifyUser;
	}

	public int getCreateUser() {
		return createUser;
	}

	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}

	public void setModifyUser(int modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getOverClassification() {
		return overClassification;
	}

	public void setOverClassification(String overClassification) {
		this.overClassification = overClassification;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getTextbookClass() {
		return textbookClass;
	}

	public void setTextbookClass(String textbookClass) {
		this.textbookClass = textbookClass;
	}

	public String getTextbookType() {
		return textbookType;
	}

	public void setTextbookType(String textbookType) {
		this.textbookType = textbookType;
	}

	public String getGroupEdit() {
		return groupEdit;
	}

	public void setGroupEdit(String groupEdit) {
		this.groupEdit = groupEdit;
	}

	public String getMainEdit() {
		return mainEdit;
	}

	public void setMainEdit(String mainEdit) {
		this.mainEdit = mainEdit;
	}

	public String getGuideTeacher() {
		return guideTeacher;
	}

	public void setGuideTeacher(String guideTeacher) {
		this.guideTeacher = guideTeacher;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getBookFormat() {
		return bookFormat;
	}

	public void setBookFormat(String bookFormat) {
		this.bookFormat = bookFormat;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getFirstChapter() {
		return firstChapter;
	}

	public void setFirstChapter(String firstChapter) {
		this.firstChapter = firstChapter;
	}

	public String getCover1() {
		return cover1;
	}

	public void setCover1(String cover1) {
		this.cover1 = cover1;
	}

	public String getCover2() {
		return cover2;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public void setCover2(String cover2) {
		this.cover2 = cover2;
	}

	public String getCover3() {
		return cover3;
	}

	public void setCover3(String cover3) {
		this.cover3 = cover3;
	}

	public String getCover4() {
		return cover4;
	}

	public void setCover4(String cover4) {
		this.cover4 = cover4;
	}

	public String getCover5() {
		return cover5;
	}

	public void setCover5(String cover5) {
		this.cover5 = cover5;
	}

	public String getCover6() {
		return cover6;
	}

	public void setCover6(String cover6) {
		this.cover6 = cover6;
	}

	public String getCover7() {
		return cover7;
	}

	public void setCover7(String cover7) {
		this.cover7 = cover7;
	}

	public String getCover8() {
		return cover8;
	}

	public void setCover8(String cover8) {
		this.cover8 = cover8;
	}

	public String getCover9() {
		return cover9;
	}

	public void setCover9(String cover9) {
		this.cover9 = cover9;
	}

	public String getCover0() {
		return cover0;
	}

	public void setCover0(String cover0) {
		this.cover0 = cover0;
	}

	public int getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(int catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getSellingStatus() {
		return sellingStatus;
	}

	public void setSellingStatus(String sellingStatus) {
		this.sellingStatus = sellingStatus;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(long modifyTime) {
		this.modifyTime = modifyTime;
	}

}
