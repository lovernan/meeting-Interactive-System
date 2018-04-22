package cn.whxlover.dao.Impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.whxlover.dao.friendinfoDao;
import cn.whxlover.domain.friends;
import cn.whxlover.domain.user;
import cn.whxlover.utils.DataSourceUtils;

public class friendinfoDaoImpl implements friendinfoDao {

	@Override
	public user friendinfo(int uid) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where uid=?";
		user user = runner.query(sql, new BeanHandler<user>(user.class), uid);
		return user;
	}

}
