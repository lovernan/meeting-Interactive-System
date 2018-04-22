package cn.whxlover.dao;

import java.sql.SQLException;

import cn.whxlover.domain.user;

public interface registerDao {

	int register(user user) throws SQLException;

}
