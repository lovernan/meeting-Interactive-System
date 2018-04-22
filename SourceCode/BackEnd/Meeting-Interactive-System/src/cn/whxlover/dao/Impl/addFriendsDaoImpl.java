package cn.whxlover.dao.Impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.whxlover.dao.addFriendsDao;
import cn.whxlover.domain.friends;
import cn.whxlover.domain.user;
import cn.whxlover.utils.DataSourceUtils;

public class addFriendsDaoImpl implements addFriendsDao {



	@Override
	public int addFriends(int uid, String username, String sign) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from friends where uid=? and fname =?";
		friends friend = runner.query(sql, new BeanHandler<friends>(friends.class), uid,username);
		if(friend==null) {
			String sql1 = "insert into friends values(null,?,?,?)";
			int success = runner.update(sql1, username,sign,uid);
			return success;			
		}else {
			return 0;
		}
		
		
		
	}
	//添加好友
	@Override
	public Boolean addFriend(String uid, String fid) throws SQLException {
		int userid = Integer.parseInt(uid);
		int friendid = Integer.parseInt(fid);
		
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where uid = ?";
		user user = runner.query(sql, new BeanHandler<user>(user.class), friendid);
		String sign = user.getSign();
		String username = user.getUsername();
		String sql1 = "insert into friends values(null,?,?,?)";
		int success = runner.update(sql1, username,sign,userid);
		if(success>0) {
			return true;
		}else {
			return false;
		}
	}

}
