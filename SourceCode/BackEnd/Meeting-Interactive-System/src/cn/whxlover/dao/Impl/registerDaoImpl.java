package cn.whxlover.dao.Impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import cn.whxlover.dao.registerDao;
import cn.whxlover.domain.user;
import cn.whxlover.utils.DataSourceUtils;

public class registerDaoImpl implements registerDao {

	public int register(user user) throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user values(null,?,?,?,?,?,?,?,?,?)";

		int issuccess = runner.update(sql,user.getUsername(),user.getPassword(),user.getEmail(),user.getImage(),user.getSign(),user.getToken(),user.getDepartment(),user.getLabel1(),user.getLabel2());
		return issuccess;
	}

}
