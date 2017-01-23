package com.lpmas.textbook.console.textbook.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.textbook.textbook.bean.ArticleInfoBean;
import com.lpmas.textbook.console.factory.TextbookDBFactory;

import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;

public class ArticleInfoDao {
	private static Logger log = LoggerFactory.getLogger(ArticleInfoDao.class);

	public int insertArticleInfo(ArticleInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into article_info ( article_title, author, section_type, article_content, publish_status, priority, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getArticleTitle());
			ps.setString(2, bean.getAuthor());
			ps.setString(3, bean.getSectionType());
			ps.setString(4, bean.getArticleContent());
			ps.setInt(5, bean.getPublishStatus());
			ps.setInt(6, bean.getPriority());
			ps.setInt(7, bean.getStatus());
			ps.setInt(8, bean.getCreateUser());
			ps.setString(9, bean.getMemo());

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

	public int updateArticleInfo(ArticleInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update article_info set article_title = ?, author = ?, section_type = ?, article_content = ?, publish_status = ?, priority = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where article_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getArticleTitle());
			ps.setString(2, bean.getAuthor());
			ps.setString(3, bean.getSectionType());
			ps.setString(4, bean.getArticleContent());
			ps.setInt(5, bean.getPublishStatus());
			ps.setInt(6, bean.getPriority());
			ps.setInt(7, bean.getStatus());
			ps.setInt(8, bean.getModifyUser());
			ps.setString(9, bean.getMemo());

			ps.setInt(10, bean.getArticleId());

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

	public ArticleInfoBean getArticleInfoByKey(int articleId) {
		ArticleInfoBean bean = null;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from article_info where article_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, articleId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new ArticleInfoBean();
				bean = BeanKit.resultSet2Bean(rs, ArticleInfoBean.class);
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

	public PageResultBean<ArticleInfoBean> getArticleInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<ArticleInfoBean> result = new PageResultBean<ArticleInfoBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from article_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			// 条件处理
			String articleTitle = condMap.get("articleTitle");
			if (StringKit.isValid(articleTitle)) {
				condList.add("article_title like ?");
				paramList.add("%" + articleTitle + "%");
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}

			String orderQuery = "order by article_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, ArticleInfoBean.class, pageBean,
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
