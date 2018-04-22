package cn.whxlover.service.impl;

import java.sql.SQLException;
import java.util.List;

import cn.whxlover.dao.friendsListDao;
import cn.whxlover.dao.Impl.friendsListDaoImpl;
import cn.whxlover.domain.friends;
import cn.whxlover.service.friendsListSer;

public class friendsListSerImpl implements friendsListSer {

	@Override
	public List<friends> friendsList(String uid) throws SQLException {
		friendsListDao friendsListDao = new friendsListDaoImpl();
		List<friends> friends = friendsListDao.friendsList(uid);
		return friends;
	}

}
