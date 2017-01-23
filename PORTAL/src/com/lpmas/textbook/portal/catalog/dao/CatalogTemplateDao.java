package com.lpmas.textbook.portal.catalog.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.textbook.catalog.bean.CatalogTemplateBean;
import com.lpmas.textbook.portal.factory.TextbookDBFactory;

public class CatalogTemplateDao {
	private static Logger log = LoggerFactory.getLogger(CatalogTemplateDao.class);

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

}
