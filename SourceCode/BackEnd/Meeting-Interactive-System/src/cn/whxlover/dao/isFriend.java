package cn.whxlover.dao;

import java.sql.SQLException;

public interface isFriend {

	Boolean isFriend(int userid, int friendid) throws SQLException;

}
