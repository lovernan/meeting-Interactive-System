package cn.whxlover.dao.Impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.whxlover.dao.friendsListDao;
import cn.whxlover.domain.friends;
import cn.whxlover.domain.user;
import cn.whxlover.utils.DataSourceUtils;

public class friendsListDaoImpl implements friendsListDao {

	@Override
	public List<friends> friendsList(String uid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from friends where uid = ? ";
		//user query = runner.query(sql, new BeanHandler<user>(user.class), "%"+text+"%");
		List<friends> friends = runner.query(sql, new BeanListHandler<friends>(friends.class), uid);
		return friends;
		
	}

	@Override
	public List<user> friendsListinfo(int uid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username in (select fname from friends where uid = ?)";
		List<user> user = runner.query(sql, new BeanListHandler<user>(user.class), uid);
		return user;
	}

}
