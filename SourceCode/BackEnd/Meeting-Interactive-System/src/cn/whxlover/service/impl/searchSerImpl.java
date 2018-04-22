package cn.whxlover.service.impl;

import java.sql.SQLException;
import java.util.List;

import cn.whxlover.dao.searchDao;
import cn.whxlover.dao.Impl.searchDaoImpl;
import cn.whxlover.domain.user;
import cn.whxlover.service.searchSer;

public class searchSerImpl implements searchSer {

	@Override
	public List<user> search(String text) throws SQLException {
		searchDao searchDao = new searchDaoImpl();
		return searchDao.search(text);
		
	}

}
