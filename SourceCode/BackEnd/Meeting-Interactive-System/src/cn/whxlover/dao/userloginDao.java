package cn.whxlover.dao;

import java.sql.SQLException;

import cn.whxlover.domain.user;

public interface userloginDao {

	user userlogin(String username, String password) throws SQLException;

	Boolean loginTocken(String uuid, int userid) throws SQLException;

	Boolean verifytoken(int userid, String token) throws SQLException;

	user userverifytoken(int userid, String token) throws SQLException;

	user find(String username) throws SQLException;

	Boolean loginin(int userid, String password) throws SQLException;


}
