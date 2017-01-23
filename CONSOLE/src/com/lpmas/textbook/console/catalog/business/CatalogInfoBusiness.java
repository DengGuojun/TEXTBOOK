package com.lpmas.textbook.console.catalog.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.textbook.catalog.bean.CatalogInfoBean;
import com.lpmas.textbook.console.catalog.dao.CatalogInfoDao;
import com.lpmas.textbook.console.textbook.business.TextbookInfoBusiness;
import com.lpmas.textbook.textbook.bean.TextbookInfoBean;

public class CatalogInfoBusiness {
	public int addCatalogInfo(CatalogInfoBean bean) {
		CatalogInfoDao dao = new CatalogInfoDao();
		return dao.insertCatalogInfo(bean);
	}

	public int updateCatalogInfo(CatalogInfoBean bean) {
		CatalogInfoDao dao = new CatalogInfoDao();
		return dao.updateCatalogInfo(bean);
	}

	public CatalogInfoBean getCatalogInfoByKey(int catalogId) {
		CatalogInfoDao dao = new CatalogInfoDao();
		return dao.getCatalogInfoByKey(catalogId);
	}

	public PageResultBean<CatalogInfoBean> getCatalogInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		CatalogInfoDao dao = new CatalogInfoDao();
		return dao.getCatalogInfoPageListByMap(condMap, pageBean);
	}

	public List<CatalogInfoBean> getCatalogListByParentId(int parentCatalogId) {
		CatalogInfoDao dao = new CatalogInfoDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("parentCatalogId", String.valueOf(parentCatalogId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		condMap.put("orderBy", "priority");
		return dao.getCatalogListByMap(condMap);
	}

	public ReturnMessageBean isHaveChild(CatalogInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		List<CatalogInfoBean> list = getCatalogListByParentId(bean.getCatalogId());
		if (!list.isEmpty()) {
			result.setMessage("有子类不可删除");
		}
		return result;
	}

	public int removeCatalogInfo(CatalogInfoBean bean) {
		int result = 0;
		CatalogInfoDao dao = new CatalogInfoDao();
		CatalogInfoBean originalBean = dao.getCatalogInfoByKey(bean.getCatalogId());
		originalBean.setStatus(Constants.STATUS_NOT_VALID);
		originalBean.setModifyUser(bean.getModifyUser());
		result = dao.updateCatalogInfo(originalBean);

		return result;
	}

	public ReturnMessageBean verifyCatalogInfo(CatalogInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getCatalogName().trim())) {
			result.setMessage("名称必须填写");
		} else if (isExistsCatalogName(bean)) {
			result.setMessage("已存在相同的分类名称");
		}
		return result;
	}

	private boolean isExistsCatalogName(CatalogInfoBean bean) {
		CatalogInfoBean existsBean = getCatalogInfoByNameWithStatus(bean.getCatalogName(), Constants.STATUS_VALID);
		if (existsBean == null) {
			return false;
		} else {
			if (existsBean.getCatalogId() == bean.getCatalogId()) {
				return false;
			}
		}
		return true;
	}

	public CatalogInfoBean getCatalogInfoByNameWithStatus(String catalogName, int status) {
		CatalogInfoDao dao = new CatalogInfoDao();
		return dao.getCatalogInfoByNameWithStatus(catalogName, status);
	}

	public ReturnMessageBean isReferenced(CatalogInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		HashMap<String, String> condMap = new HashMap<String, String>();
		TextbookInfoBusiness infoBusiness = new TextbookInfoBusiness();
		condMap.put("status", Constants.STATUS_VALID + "");
		condMap.put("catalogId", bean.getCatalogId() + "");
		PageResultBean<TextbookInfoBean> resultBean = infoBusiness.getTextbookInfoPageListByMapConneCatalog(condMap,
				new PageBean(1, 20));
		if (!resultBean.getRecordList().isEmpty()) {

			result.setMessage("此专业被引用，不允许删除");
		}
		return result;
	}

}