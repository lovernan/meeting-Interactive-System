package cn.whxlover.service.impl;

import java.sql.SQLException;

import cn.whxlover.dao.addFriendsDao;
import cn.whxlover.dao.Impl.addFriendsDaoImpl;
import cn.whxlover.service.addFriendsSer;

public class addFriendsSerImpl implements addFriendsSer {

	@Override
	public int addFriends(int uid, String username, String sign) throws SQLException {
		addFriendsDao addFriendsDao = new addFriendsDaoImpl();
		int is = addFriendsDao.addFriends(uid,username,sign);
		return is;
	}

}
