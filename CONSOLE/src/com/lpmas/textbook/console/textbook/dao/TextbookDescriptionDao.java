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
import com.lpmas.textbook.textbook.bean.TextbookDescriptionBean;

public class TextbookDescriptionDao {
	private static Logger log = LoggerFactory.getLogger(TextbookDescriptionDao.class);

	public int insertTextbookDescription(TextbookDescriptionBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into textbook_description ( textbook_id, desc_code, desc_value, create_time, create_user) value( ?, ?, ?, now(), ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getTextbookId());
			ps.setString(2, bean.getDescCode());
			ps.setString(3, bean.getDescValue());
			ps.setInt(4, bean.getCreateUser());

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

	public int updateTextbookDescription(TextbookDescriptionBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update textbook_description set desc_value = ?, modify_time = now(), modify_user = ? where textbook_id = ? and desc_code = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getDescValue());
			ps.setInt(2, bean.getModifyUser());

			ps.setInt(3, bean.getTextbookId());
			ps.setString(4, bean.getDescCode());

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

	public TextbookDescriptionBean getTextbookDescriptionByKey(int textbookId, String descCode) {
		TextbookDescriptionBean bean = null;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from textbook_description where textbook_id = ? and desc_code = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, textbookId);
			ps.setString(2, descCode);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TextbookDescriptionBean();
				bean = BeanKit.resultSet2Bean(rs, TextbookDescriptionBean.class);
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

	public List<TextbookDescriptionBean> getTextbookDescriptionListById(int textbookId) {
		List<TextbookDescriptionBean> list = new ArrayList<TextbookDescriptionBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from textbook_description where textbook_id = ? ";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, textbookId);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				TextbookDescriptionBean bean = BeanKit.resultSet2Bean(rs, TextbookDescriptionBean.class);
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("", e);
			list = null;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return list;
	}

	public PageResultBean<TextbookDescriptionBean> getTextbookDescriptionPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<TextbookDescriptionBean> result = new PageResultBean<TextbookDescriptionBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from textbook_description";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by textbook_id,desc_code desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, TextbookDescriptionBean.class,
					pageBean, db);
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