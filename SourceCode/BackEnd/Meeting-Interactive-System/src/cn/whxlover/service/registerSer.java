package cn.whxlover.service;

import java.sql.SQLException;

import cn.whxlover.domain.user;

public interface registerSer {

	int register(user user) throws SQLException;

}
