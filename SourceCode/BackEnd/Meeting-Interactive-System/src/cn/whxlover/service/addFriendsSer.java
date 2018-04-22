package cn.whxlover.service;

import java.sql.SQLException;

public interface addFriendsSer {

	int addFriends(int uid, String username, String sign) throws SQLException;

}
