package cn.whxlover.service.impl;

import java.sql.SQLException;

import cn.whxlover.dao.registerDao;
import cn.whxlover.dao.Impl.registerDaoImpl;
import cn.whxlover.domain.user;
import cn.whxlover.service.registerSer;

public class registerSerImpl implements registerSer {

	@Override
	public int register(user user) throws SQLException {
		registerDao registerDao = new registerDaoImpl();
		return registerDao.register(user);
		
	}

}
