package com.lpmas.textbook.portal.catalog.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.textbook.catalog.bean.CatalogInfoBean;
import com.lpmas.textbook.portal.factory.TextbookDBFactory;

public class CatalogInfoDao {
	private static Logger log = LoggerFactory.getLogger(CatalogInfoDao.class);

	public List<CatalogInfoBean> getCatalogListByMap(HashMap<String, String> condMap) {
		List<CatalogInfoBean> result = new ArrayList<CatalogInfoBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from catalog_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String parentCatalogId = condMap.get("parentCatalogId");
			if (StringKit.isValid(parentCatalogId)) {
				condList.add("parent_catalog_id = ?");
				paramList.add(parentCatalogId);
			}
			String textbookId = condMap.get("textbookId");
			if (StringKit.isValid(textbookId)) {
				condList.add("textbook_id = ?");
				paramList.add(textbookId);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by catalog_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, CatalogInfoBean.class, db);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

	public CatalogInfoBean getCatalogInfoByCode(String catalogCode) {
		CatalogInfoBean bean = null;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from catalog_info where  catalog_code = ? and is_display = " + Constants.STATUS_VALID
					+ " and status = " + Constants.STATUS_VALID;
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, catalogCode);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new CatalogInfoBean();
				bean = BeanKit.resultSet2Bean(rs, CatalogInfoBean.class);
			}
			rs.close();
		} catch (Exception e) {
			log.error("", e);
			bean = null;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return bean;
	}

	public List<CatalogInfoBean> getCatalogInfoListByParentCatalogId(int parentCatalogId, String orderBy) {
		List<CatalogInfoBean> list = new ArrayList<CatalogInfoBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from catalog_info where parent_catalog_id = ? and is_display = "
					+ Constants.STATUS_VALID + " and status = " + Constants.STATUS_VALID + " order by " + orderBy;
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, parentCatalogId);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				CatalogInfoBean bean = new CatalogInfoBean();
				bean = BeanKit.resultSet2Bean(rs, CatalogInfoBean.class);
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return list;
	}

	public CatalogInfoBean getCatalogInfoByKey(int catalogId) {
		CatalogInfoBean bean = null;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from catalog_info where catalog_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, catalogId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new CatalogInfoBean();
				bean = BeanKit.resultSet2Bean(rs, CatalogInfoBean.class);
			}
			rs.close();
		} catch (Exception e) {
			log.error("", e);
			bean = null;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return bean;
	}

}
