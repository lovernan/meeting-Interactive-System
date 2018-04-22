package cn.whxlover.dao.Impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.whxlover.domain.user;
import cn.whxlover.utils.DataSourceUtils;

public class obtainDaoImpl {

	public user obtain(int userid) throws SQLException {
			QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
			String sql = "select * from user where uid = ? ";
			user query = runner.query(sql, new BeanHandler<user>(user.class), userid);
			return query;
		
	}



}
