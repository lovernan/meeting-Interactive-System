package cn.whxlover.dao.Impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.whxlover.dao.searchDao;
import cn.whxlover.domain.user;
import cn.whxlover.utils.DataSourceUtils;

public class searchDaoImpl implements searchDao {

	@Override
	//��������
	public List<user> search(String text) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username like ? ";
		//user query = runner.query(sql, new BeanHandler<user>(user.class), "%"+text+"%");
		List<user> userlist = runner.query(sql, new BeanListHandler<user>(user.class), "%"+text+"%");
		return userlist;
	}
	//��ѯ�û���uid
	@Override
	public String searchid(String username) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username =?";
		user user = runner.query(sql, new BeanHandler<user>(user.class), username);
		int userid = user.getUid();
		String userId = Integer.toString(userid);
		return userId;
	}
	//查询好友
	public user searchuser(String username) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username =?";
		user user = runner.query(sql, new BeanHandler<user>(user.class), username);
		return user;
	}

}
