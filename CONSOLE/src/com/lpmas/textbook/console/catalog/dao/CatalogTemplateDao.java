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
import com.lpmas.textbook.catalog.bean.CatalogTemplateBean;
import com.lpmas.textbook.console.factory.TextbookDBFactory;

public class CatalogTemplateDao {
	private static Logger log = LoggerFactory.getLogger(CatalogTemplateDao.class);

	public int insertCatalogTemplate(CatalogTemplateBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into catalog_template ( catalog_id, language, template_type, template_id, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getCatalogId());
			ps.setString(2, bean.getLanguage());
			ps.setString(3, bean.getTemplateType());
			ps.setInt(4, bean.getTemplateId());
			ps.setInt(5, bean.getStatus());
			ps.setInt(6, bean.getCreateUser());
			ps.setString(7, bean.getMemo());

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

	public int updateCatalogTemplate(CatalogTemplateBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update catalog_template set template_id = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where catalog_id = ? and language = ? and template_type = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getTemplateId());
			ps.setInt(2, bean.getStatus());
			ps.setInt(3, bean.getModifyUser());
			ps.setString(4, bean.getMemo());

			ps.setInt(5, bean.getCatalogId());
			ps.setString(6, bean.getLanguage());
			ps.setString(7, bean.getTemplateType());

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

	public CatalogTemplateBean getCatalogTemplateByKey(int catalogId, String language, String templateType) {
		CatalogTemplateBean bean = null;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from catalog_template where catalog_id = ? and language = ? and template_type = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, catalogId);
			ps.setString(2, language);
			ps.setString(3, templateType);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new CatalogTemplateBean();
				bean = BeanKit.resultSet2Bean(rs, CatalogTemplateBean.class);
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

	public PageResultBean<CatalogTemplateBean> getCatalogTemplatePageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<CatalogTemplateBean> result = new PageResultBean<CatalogTemplateBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from catalog_template";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by catalog_id,language,template_type desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, CatalogTemplateBean.class, pageBean,
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

	public List<CatalogTemplateBean> getCatalogTemplateListByKey(int catalogId, String language) {
		List<CatalogTemplateBean> list = new ArrayList<CatalogTemplateBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from catalog_template where catalog_id = ? and language = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, catalogId);
			ps.setString(2, language);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				CatalogTemplateBean bean = new CatalogTemplateBean();
				bean = BeanKit.resultSet2Bean(rs, CatalogTemplateBean.class);

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

}
