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
import com.lpmas.textbook.textbook.bean.TextbookMediaBean;

public class TextbookMediaDao {
	private static Logger log = LoggerFactory.getLogger(TextbookMediaDao.class);

	public int insertTextbookMedia(TextbookMediaBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into textbook_media ( textbook_id, media_type, file_type, media_url, create_time, create_user) value( ?, ?, ?, ?, now(), ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getTextbookId());
			ps.setString(2, bean.getMediaType());
			ps.setString(3, bean.getFileType());
			ps.setString(4, bean.getMediaUrl());
			ps.setInt(5, bean.getCreateUser());

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

	public int updateTextbookMedia(TextbookMediaBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update textbook_media set file_type = ?, media_url = ?, modify_time = now(), modify_user = ? where textbook_id = ? and media_type = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getFileType());
			ps.setString(2, bean.getMediaUrl());
			ps.setInt(3, bean.getModifyUser());

			ps.setInt(4, bean.getTextbookId());
			ps.setString(5, bean.getMediaType());

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

	public TextbookMediaBean getTextbookMediaByKey(int textbookId, String mediaType) {
		TextbookMediaBean bean = null;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from textbook_media where textbook_id = ? and media_type = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, textbookId);
			ps.setString(2, mediaType);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TextbookMediaBean();
				bean = BeanKit.resultSet2Bean(rs, TextbookMediaBean.class);
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

	public PageResultBean<TextbookMediaBean> getTextbookMediaPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<TextbookMediaBean> result = new PageResultBean<TextbookMediaBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from textbook_media";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by media_type,textbook_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, TextbookMediaBean.class, pageBean,
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

	public int getTextbookCoverNumById(int textbookId) {
		int result = 0;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from textbook_media where textbook_id = ? ";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, textbookId);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				result++;
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
		return result;
	}

	public List<TextbookMediaBean> getTextbookMediaListById(int textbookId) {
		List<TextbookMediaBean> list = new ArrayList<TextbookMediaBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from textbook_media where textbook_id = ? ";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, textbookId);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				TextbookMediaBean bean = new TextbookMediaBean();
				bean = BeanKit.resultSet2Bean(rs, TextbookMediaBean.class);
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

	public int removeTextbookMediaById(int textbookId) {
		int result = 0;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "delete from textbook_media where textbook_id = ? ";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, textbookId);
			ps.execute();
			result = ps.getUpdateCount();
		} catch (Exception e) {
			log.error("", e);
			result = 0;
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