package com.lpmas.textbook.console.textbook.dao;

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
import com.lpmas.textbook.console.factory.TextbookDBFactory;
import com.lpmas.textbook.textbook.bean.CatalogTextbookBean;

public class CatalogTextbookDao {
	private static Logger log = LoggerFactory.getLogger(CatalogTextbookDao.class);

	public int insertCatalogTextbook(CatalogTextbookBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into catalog_textbook ( catalog_id, textbook_id, priority, status, create_time, create_user, memo) value( ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getCatalogId());
			ps.setInt(2, bean.getTextbookId());
			ps.setInt(3, bean.getPriority());
			ps.setInt(4, bean.getStatus());
			ps.setInt(5, bean.getCreateUser());
			ps.setString(6, bean.getMemo());

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

	public int updateCatalogTextbook(CatalogTextbookBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update catalog_textbook set priority = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where catalog_id = ? and textbook_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getPriority());
			ps.setInt(2, bean.getStatus());
			ps.setInt(3, bean.getModifyUser());
			ps.setString(4, bean.getMemo());

			ps.setInt(5, bean.getCatalogId());
			ps.setInt(6, bean.getTextbookId());
			System.out.println("" + ps.toString());
			result = db.executePstmtUpdate();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
			e.printStackTrace();
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

	public int updateCatalogTextbookByBookId(CatalogTextbookBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update catalog_textbook set priority = ?, status = ?, modify_time = now(), modify_user = ?, memo = ?, catalog_id = ? where textbook_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getPriority());
			ps.setInt(2, bean.getStatus());
			ps.setInt(3, bean.getModifyUser());
			ps.setString(4, bean.getMemo());

			ps.setInt(5, bean.getCatalogId());
			ps.setInt(6, bean.getTextbookId());
			System.out.println("" + ps.toString());
			result = db.executePstmtUpdate();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
			e.printStackTrace();
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

	public CatalogTextbookBean getCatalogTextbookByKey(int catalogId, int textbookId) {
		CatalogTextbookBean bean = null;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from catalog_textbook where catalog_id = ? and textbook_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, catalogId);
			ps.setInt(2, textbookId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new CatalogTextbookBean();
				bean = BeanKit.resultSet2Bean(rs, CatalogTextbookBean.class);
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

	public List<CatalogTextbookBean> getCatalogTextbookListByCatalogId(int catalogId) {
		List<CatalogTextbookBean> list = new ArrayList<CatalogTextbookBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from catalog_textbook where catalog_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, catalogId);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				CatalogTextbookBean bean = new CatalogTextbookBean();
				bean = BeanKit.resultSet2Bean(rs, CatalogTextbookBean.class);
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

	public CatalogTextbookBean getCatalogTextbookByBookId(int textbookId) {
		CatalogTextbookBean bean = null;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from catalog_textbook where textbook_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, textbookId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new CatalogTextbookBean();
				bean = BeanKit.resultSet2Bean(rs, CatalogTextbookBean.class);
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

	public PageResultBean<CatalogTextbookBean> getCatalogTextbookPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<CatalogTextbookBean> result = new PageResultBean<CatalogTextbookBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from catalog_textbook";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by catalog_id,textbook_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, CatalogTextbookBean.class, pageBean,
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
