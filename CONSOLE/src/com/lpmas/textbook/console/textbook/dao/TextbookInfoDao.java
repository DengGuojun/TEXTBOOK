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
import com.lpmas.textbook.textbook.bean.TextbookInfoBean;

public class TextbookInfoDao {
	private static Logger log = LoggerFactory.getLogger(TextbookInfoDao.class);

	public int insertTextbookInfo(TextbookInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into textbook_info ( textbook_name, priority, selling_status, status, create_time, create_user, memo) value( ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getTextbookName());
			ps.setInt(2, bean.getPriority());
			ps.setString(3, bean.getSellingStatus());
			ps.setInt(4, bean.getStatus());
			ps.setInt(5, bean.getCreateUser());
			ps.setString(6, bean.getMemo());

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

	public int updateTextbookInfo(TextbookInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update textbook_info set textbook_name = ?, priority = ?, selling_status = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where textbook_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getTextbookName());
			ps.setInt(2, bean.getPriority());
			ps.setString(3, bean.getSellingStatus());
			ps.setInt(4, bean.getStatus());
			ps.setInt(5, bean.getModifyUser());
			ps.setString(6, bean.getMemo());

			ps.setInt(7, bean.getTextbookId());

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

	public TextbookInfoBean getTextbookInfoByKey(int textbookId) {
		TextbookInfoBean bean = null;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from textbook_info where textbook_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, textbookId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TextbookInfoBean();
				bean = BeanKit.resultSet2Bean(rs, TextbookInfoBean.class);
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

	public PageResultBean<TextbookInfoBean> getTextbookInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<TextbookInfoBean> result = new PageResultBean<TextbookInfoBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from textbook_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();

			// 条件处理
			String textbookName = condMap.get("textbookName");
			if (StringKit.isValid(textbookName)) {
				condList.add("textbook_name like ?");
				paramList.add("%" + textbookName + "%");
			}

			String orderQuery = "order by textbook_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, TextbookInfoBean.class, pageBean,
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

	public PageResultBean<TextbookInfoBean> getTextbookInfoPageListByMapConneCatalog(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<TextbookInfoBean> result = new PageResultBean<TextbookInfoBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select textbook_info.* from textbook_info , catalog_textbook";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();

			// 条件处理
			condList.add("textbook_info.textbook_id=catalog_textbook.textbook_id");

			String textbookName = condMap.get("textbookName");
			if (StringKit.isValid(textbookName)) {
				condList.add("textbook_name like ?");
				paramList.add("%" + textbookName + "%");
			}
			String catalogId = condMap.get("catalogId");
			if (StringKit.isValid(catalogId)) {
				condList.add("catalog_id = ?");
				paramList.add(catalogId);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("textbook_info.status = ?");
				paramList.add(status);
			}

			String orderQuery = "order by textbook_info.textbook_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}
			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, TextbookInfoBean.class, pageBean,
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

	public TextbookInfoBean getTextbookInfoByNameWithStatus(String textbookName, int status) {
		TextbookInfoBean bean = null;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from textbook_info where textbook_Name = ? and status = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, textbookName);
			ps.setInt(2, status);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TextbookInfoBean();
				bean = BeanKit.resultSet2Bean(rs, TextbookInfoBean.class);
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

	public PageResultBean<TextbookInfoBean> getTextbookInfoPageListByMapConneDesc(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<TextbookInfoBean> result = new PageResultBean<TextbookInfoBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select textbook_info.* from textbook_info , textbook_description";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();

			// 条件处理
			condList.add("textbook_info.textbook_id=textbook_description.textbook_id");
			String textbookName = condMap.get("textbookName");
			if (StringKit.isValid(textbookName)) {
				condList.add("textbook_name like ?");
				paramList.add("%" + textbookName + "%");
			}
			String descCode = condMap.get("descCode");
			if (StringKit.isValid(descCode)) {
				condList.add("desc_code = ?");
				paramList.add(descCode);
			}
			String descValue = condMap.get("descValue");
			if (StringKit.isValid(descValue)) {
				condList.add("desc_value = ?");
				paramList.add(descValue);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("textbook_info.status = ?");
				paramList.add(status);
			}

			String orderQuery = "order by textbook_info.textbook_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}
			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, TextbookInfoBean.class, pageBean,
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

	public List<TextbookInfoBean> getTextbookInfoListByMap(HashMap<String, String> condMap) {
		List<TextbookInfoBean> result = new ArrayList<TextbookInfoBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from textbook_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();

			// 条件处理
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("textbook_info.status = ?");
				paramList.add(status);
			}

			String orderQuery = "order by textbook_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, TextbookInfoBean.class, db);
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
