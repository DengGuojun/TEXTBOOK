package com.lpmas.textbook.portal.textbook.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.textbook.textbook.bean.ArticleInfoBean;
import com.lpmas.textbook.portal.factory.TextbookDBFactory;
import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;

public class ArticleInfoDao {
	private static Logger log = LoggerFactory.getLogger(ArticleInfoDao.class);

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

	public PageResultBean<ArticleInfoBean> getRotationInfoPageListByMap(HashMap<String, String> condMap,
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
			String sectionType = condMap.get("sectionType");
			if (StringKit.isValid(sectionType)) {
				condList.add("section_type like ?");
				paramList.add(sectionType);
			}
			condList.add("status = ?");
			paramList.add("1");

			String orderQuery = "order by priority,create_time desc";
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
