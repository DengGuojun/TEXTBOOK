package com.lpmas.textbook.portal.catalog.business;

public class CatalogDisplayKit {
	CatalogInfoBusiness catalogInfoBusiness = null;

	private CatalogInfoBusiness getCatalogInfoBusiness() {
		if (null == catalogInfoBusiness) {
			catalogInfoBusiness = new CatalogInfoBusiness();
		}
		return catalogInfoBusiness;
	}
}
