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
import com.lpmas.textbook.textbook.bean.MediaInfoBean;

public class MediaInfoDao {
	private static Logger log = LoggerFactory.getLogger(MediaInfoDao.class);

	public int insertMediaInfo(MediaInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into media_info ( medial_path, status, create_time, create_user, memo) value( ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getMedialPath());
			ps.setInt(2, bean.getStatus());
			ps.setInt(3, bean.getCreateUser());
			ps.setString(4, bean.getMemo());

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

	public int updateMediaInfo(MediaInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update media_info set medial_path = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where media_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getMedialPath());
			ps.setInt(2, bean.getStatus());
			ps.setInt(3, bean.getModifyUser());
			ps.setString(4, bean.getMemo());

			ps.setInt(5, bean.getMediaId());

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

	public MediaInfoBean getMediaInfoByKey(int mediaId) {
		MediaInfoBean bean = null;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from media_info where media_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, mediaId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new MediaInfoBean();
				bean = BeanKit.resultSet2Bean(rs, MediaInfoBean.class);
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

	public MediaInfoBean getMediaInfoByPath(String path) {
		MediaInfoBean bean = null;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from media_info where medial_path = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, path);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new MediaInfoBean();
				bean = BeanKit.resultSet2Bean(rs, MediaInfoBean.class);
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

	public PageResultBean<MediaInfoBean> getMediaInfoPageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		PageResultBean<MediaInfoBean> result = new PageResultBean<MediaInfoBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from media_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}

			String orderQuery = "order by media_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, MediaInfoBean.class, pageBean, db);
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

	public List<MediaInfoBean> getMediaInfoList(HashMap<String, String> condMap) {
		List<MediaInfoBean> list = new ArrayList<MediaInfoBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from media_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by media_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			list = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, MediaInfoBean.class, db);
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
