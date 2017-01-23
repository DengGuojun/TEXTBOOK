package com.lpmas.textbook.portal.textbook.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.textbook.portal.factory.TextbookDBFactory;
import com.lpmas.textbook.textbook.bean.RotationInfoBean;

import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.util.StringKit;

public class RotationInfoDao {
	private static Logger log = LoggerFactory.getLogger(RotationInfoDao.class);

	public List<RotationInfoBean> getRotationInfoListByMap(HashMap<String, String> condMap) {
		List<RotationInfoBean> result = new ArrayList<RotationInfoBean>();
		DBFactory dbFactory = new TextbookDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from rotation_info where status = 1";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by priority desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}
			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, RotationInfoBean.class, db);
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
