package cn.whxlover.service;

import java.sql.SQLException;

import cn.whxlover.domain.user;

public interface loginSer {

	user userlogin(String username, String password) throws SQLException;

	Boolean loginTocken(String uuid, int userid) throws SQLException;

	Boolean verifyToken(int userid, String token) throws SQLException;

	user userVerifyToken(int userid, String token) throws SQLException;

	user find(String username) throws SQLException;

	Boolean loginin(int userid, String password) throws SQLException;
}
