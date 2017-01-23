package com.lpmas.textbook.portal.product.bean;

public class ProductIndexBean {
	private String storeId = ""; // 店铺ID
	private int productId = 0; // 商品ID
	private String productNumber = ""; // sku
	private String productTitle = ""; // 商品名称、商品描述中获取
	private String listPrice = ""; // 吊牌价格
	private String offerPrice = "";// 价格，用币种:金额#分割
	private String catalogId = ""; // 销售类目，catalog_info.catalog_id
	private String catalogPriority = ""; // 销售类目排序catalog_id#priority
	private String specificationNumber = ""; // 规格编码
	private String specificationDesc = "";// 规格描述
	private String isBuyable = ""; // catalog_product.is_buyable
									// 格式：storeId:是否可购买 多个店铺#号分隔
	private String isDisplay = ""; // catalog_product.is_buyable 格式：storeId:是否显示
	private String keywords = ""; // product_info.keywords
	private String listedDate = ""; // product_info.listed_date
	private int brandId = 0; // commodity_info.brand
	private int businessMode = 0; // 经营方式
	private int salesMode = 0; // 销售方式
	private int productMode = 0; // 商品类型
	private int payMode = 0; // 支付方式
	private String productDescription = "";// 从product_description获取，存入json格式
	private String productItemList = ""; // 存入json格式

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
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

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatalogPriority() {
		return catalogPriority;
	}

	public void setCatalogPriority(String catalogPriority) {
		this.catalogPriority = catalogPriority;
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

	public String getIsBuyable() {
		return isBuyable;
	}

	public void setIsBuyable(String isBuyable) {
		this.isBuyable = isBuyable;
	}

	public String getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getListedDate() {
		return listedDate;
	}

	public void setListedDate(String listedDate) {
		this.listedDate = listedDate;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public int getBusinessMode() {
		return businessMode;
	}

	public void setBusinessMode(int businessMode) {
		this.businessMode = businessMode;
	}

	public int getSalesMode() {
		return salesMode;
	}

	public void setSalesMode(int salesMode) {
		this.salesMode = salesMode;
	}

	public int getProductMode() {
		return productMode;
	}

	public void setProductMode(int productMode) {
		this.productMode = productMode;
	}

	public int getPayMode() {
		return payMode;
	}

	public void setPayMode(int payMode) {
		this.payMode = payMode;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductItemList() {
		return productItemList;
	}

	public void setProductItemList(String productItemList) {
		this.productItemList = productItemList;
	}
}
