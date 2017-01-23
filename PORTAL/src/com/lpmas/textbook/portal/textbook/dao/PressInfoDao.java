package com.lpmas.textbook.portal.textbook.dao;

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
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.textbook.portal.factory.TextbookDBFactory;
import com.lpmas.textbook.textbook.bean.PressInfoBean;

public class PressInfoDao {
	private static Logger log = LoggerFactory.getLogger(PressInfoDao.class);

	public PressInfoBean getPressInfoByKey(int pressId) {
		PressInfoBean bean = null;
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from press_info where press_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, pressId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new PressInfoBean();
				bean = BeanKit.resultSet2Bean(rs, PressInfoBean.class);
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

	public List<PressInfoBean> getPressInfoListByMap(HashMap<String, String> condMap) {
		List<PressInfoBean> result = new ArrayList<PressInfoBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from press_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String pressName = condMap.get("pressName");
			if (StringKit.isValid(pressName)) {
				condList.add("press_name like ?");
				paramList.add("% + pressName + %");
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by press_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, PressInfoBean.class, db);
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
