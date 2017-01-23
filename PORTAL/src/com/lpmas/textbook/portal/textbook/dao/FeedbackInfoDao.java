package com.lpmas.textbook.portal.textbook.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.textbook.textbook.bean.FeedbackInfoBean;
import com.lpmas.textbook.portal.factory.TextbookDBFactory;

import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;

public class FeedbackInfoDao {
	private static Logger log = LoggerFactory.getLogger(FeedbackInfoDao.class);

	public int insertFeedbackInfo(FeedbackInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into feedback_info ( textbook_name, contact_info, status, create_time, create_user, memo) value( ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getTextbookName());
			ps.setString(2, bean.getContactInfo());
			ps.setInt(3, bean.getStatus());
			ps.setInt(4, bean.getCreateUser());
			ps.setString(5, bean.getMemo());

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

}
