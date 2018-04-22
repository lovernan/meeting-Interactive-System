package cn.whxlover.dao.Impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.whxlover.dao.userloginDao;
import cn.whxlover.domain.user;
import cn.whxlover.utils.DataSourceUtils;

public class userloginDaoImpl implements userloginDao {

	@Override
	public user userlogin(String username, String password) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username=? and password=?";
		user user = runner.query(sql, new BeanHandler<user>(user.class), username,password);
		return user;
	}

	@Override
	public Boolean loginTocken(String uuid,int userid) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set token =? where uid=?";
		int succ = runner.update(sql, uuid,userid);
		if(succ>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Boolean verifytoken(int userid, String token) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where uid =? and token = ?";
		user user = runner.query(sql, new BeanHandler<user>(user.class), userid,token);
		if(user!=null) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public user userverifytoken(int userid, String token) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where uid =? and token = ?";
		user user = runner.query(sql, new BeanHandler<user>(user.class), userid,token);
		return user;
	}

	@Override
	public user find(String username) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username = ?";
		System.out.println(12+username);
		user user = runner.query(sql, new BeanHandler<user>(user.class),username);
		
		return user;
	}

	public Boolean loginin(int userid, String password) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where uid =? and password = ?";
		user user = runner.query(sql, new BeanHandler<user>(user.class), userid,password);
		System.out.println(user);
		if(user!=null) {
			return true;
		}else {
			return false;
		}
	}

}
