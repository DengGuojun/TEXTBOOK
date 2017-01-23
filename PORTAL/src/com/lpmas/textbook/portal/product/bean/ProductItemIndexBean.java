package com.lpmas.textbook.portal.product.bean;

public class ProductItemIndexBean {
	private int productItemId = 0;
	private String productItemName = "";
	private String productItemNumber = "";
	private String specificationNumber = "";
	private String specificationDesc = "";
	private String barcode = "";
	private String listPrice = "";
	private String offerPrice = "";

	public int getProductItemId() {
		return productItemId;
	}

	public void setProductItemId(int productItemId) {
		this.productItemId = productItemId;
	}

	public String getProductItemName() {
		return productItemName;
	}

	public void setProductItemName(String productItemName) {
		this.productItemName = productItemName;
	}

	public String getProductItemNumber() {
		return productItemNumber;
	}

	public void setProductItemNumber(String productItemNumber) {
		this.productItemNumber = productItemNumber;
	}

	public String getSpecificationNumber() {
		return specificationNumber;
	}

	public void setSpecificationNumber(String specificationNumber) {
		this.specificationNumber = specificationNumber;
	}

	public String getSpecificationDesc() {
		return specificationDesc;
	}

	public void setSpecificationDesc(String specificationDesc) {
		this.specificationDesc = specificationDesc;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getListPrice() {
		return listPrice;
	}

	public void setListPrice(String listPrice) {
		this.listPrice = listPrice;
	}

	public String getOfferPrice() {
		return offerPrice;
	}

	public void setOfferPrice(String offerPrice) {
		this.offerPrice = offerPrice;
	}
}
