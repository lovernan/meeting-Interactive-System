package cn.whxlover.service;

import java.sql.SQLException;
import java.util.List;

import cn.whxlover.domain.user;

public interface searchSer {

	List<user> search(String text) throws SQLException;

}
