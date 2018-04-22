package cn.whxlover.dao;

import java.sql.SQLException;
import java.util.List;

import cn.whxlover.domain.user;

public interface searchDao {

	List<user> search(String text) throws SQLException;

	String searchid(String username) throws SQLException;

}
