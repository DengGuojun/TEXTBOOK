package com.lpmas.textbook.console.catalog.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.textbook.catalog.bean.CatalogInfoBean;
import com.lpmas.textbook.console.factory.TextbookDBFactory;

public class CatalogInfoDao {
	private static Logger log = LoggerFactory.getLogger(CatalogInfoDao.class);

	public int insertCatalogInfo(CatalogInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into catalog_info ( catalog_name, parent_catalog_id, is_display, priority, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getCatalogName());
			ps.setInt(2, bean.getParentCatalogId());
			ps.setInt(3, bean.getIsDisplay());
			ps.setInt(4, bean.getPriority());
			ps.setInt(5, bean.getStatus());
			ps.setInt(6, bean.getCreateUser());
			ps.setString(7, bean.getMemo());

			result = db.executePstmtInsert();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

	public int updateCatalogInfo(CatalogInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update catalog_info set catalog_name = ?, parent_catalog_id = ?, is_display = ?, priority = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where catalog_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getCatalogName());
			ps.setInt(2, bean.getParentCatalogId());
			ps.setInt(3, bean.getIsDisplay());
			ps.setInt(4, bean.getPriority());
			ps.setInt(5, bean.getStatus());
			ps.setInt(6, bean.getModifyUser());
			ps.setString(7, bean.getMemo());
			ps.setInt(8, bean.getCatalogId());

			result = db.executePstmtUpdate();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

	public CatalogInfoBean getCatalogInfoByNameWithStatus(String catalogName, int status) {
		CatalogInfoBean bean = null;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from catalog_info where catalog_name = ? and status = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, catalogName);
			ps.setInt(2, status);

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

	public PageResultBean<CatalogInfoBean> getCatalogInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<CatalogInfoBean> result = new PageResultBean<CatalogInfoBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from catalog_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String catalogName = condMap.get("catalogName");
			if (StringKit.isValid(catalogName)) {
				condList.add("catalog_name like ?");
				paramList.add("%" + catalogName + "%");
			}
			String parentCatalogId = condMap.get("parentCatalogId");
			if (StringKit.isValid(parentCatalogId)) {
				condList.add("parent_catalog_id = ?");
				paramList.add(parentCatalogId);
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
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, CatalogInfoBean.class, pageBean,
					db);
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

}
