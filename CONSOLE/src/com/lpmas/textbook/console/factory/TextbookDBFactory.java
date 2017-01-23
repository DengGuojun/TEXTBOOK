package com.lpmas.textbook.console.factory;

import java.sql.SQLException;

import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.db.MysqlDBExecutor;
import com.lpmas.framework.db.MysqlDBObject;
import com.lpmas.textbook.console.config.TextbookDBConfig;

public class TextbookDBFactory extends DBFactory {

	@Override
	public DBExecutor getDBExecutor() {
		return new MysqlDBExecutor();
	}

	@Override
	public DBObject getDBObjectR() throws SQLException {
		return new MysqlDBObject(TextbookDBConfig.DB_LINK_TEXTBOOK_R);
	}

	@Override
	public DBObject getDBObjectW() throws SQLException {
		return new MysqlDBObject(TextbookDBConfig.DB_LINK_TEXTBOOK_W);
	}

	@Override
	protected void init() {

	}

}
