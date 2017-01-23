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
import com.lpmas.textbook.textbook.bean.KeywordInfoBean;

public class KeywordInfoDao {
	private static Logger log = LoggerFactory.getLogger(KeywordInfoDao.class);

	public int insertKeywordInfo(KeywordInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into keyword_info ( keyword, status, priority, create_time, create_user) value( ?, ?, ?, now(), ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getKeyword());
			ps.setInt(2, bean.getStatus());
			ps.setInt(3, bean.getPriority());
			ps.setInt(4, bean.getCreateUser());

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

	public int updateKeywordInfo(KeywordInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update keyword_info set keyword = ?, status = ?, priority = ?, modify_time = now(), modify_user = ? where keyword_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getKeyword());
			ps.setInt(2, bean.getStatus());
			ps.setInt(3, bean.getPriority());
			ps.setInt(4, bean.getModifyUser());

			ps.setInt(5, bean.getKeywordId());

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

	public KeywordInfoBean getKeywordInfoByKey(int keywordId) {
		KeywordInfoBean bean = null;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from keyword_info where keyword_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, keywordId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new KeywordInfoBean();
				bean = BeanKit.resultSet2Bean(rs, KeywordInfoBean.class);
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

	public PageResultBean<KeywordInfoBean> getKeywordInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<KeywordInfoBean> result = new PageResultBean<KeywordInfoBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from keyword_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String keyword = condMap.get("keyword");
			if (StringKit.isValid(keyword)) {
				condList.add("keyword like ?");
				paramList.add("%" + keyword + "%");
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by keyword_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, KeywordInfoBean.class, pageBean,
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

	public KeywordInfoBean getKeywordInfoByNameWithStatus(String keyword, int status) {
		KeywordInfoBean bean = null;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from keyword_info where keyword = ? and status = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, keyword);
			ps.setInt(2, status);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new KeywordInfoBean();
				bean = BeanKit.resultSet2Bean(rs, KeywordInfoBean.class);
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