package cn.whxlover.service.impl;

import java.sql.SQLException;

import cn.whxlover.dao.friendinfoDao;
import cn.whxlover.dao.Impl.friendinfoDaoImpl;
import cn.whxlover.domain.friends;
import cn.whxlover.domain.user;
import cn.whxlover.service.friendinfo;

public class friendinfoImpl implements friendinfo {

	@Override
	public user friendinfomation(int uid) throws SQLException {
		// TODO Auto-generated method stub
		friendinfoDao friendinfoDao = new friendinfoDaoImpl();
		return friendinfoDao.friendinfo(uid);
		
	}

}
