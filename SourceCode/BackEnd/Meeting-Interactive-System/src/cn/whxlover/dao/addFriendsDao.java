package cn.whxlover.dao;

import java.sql.SQLException;

public interface addFriendsDao {

	int addFriends(int uid, String username, String sign) throws SQLException;

	Boolean addFriend(String uid, String fid) throws SQLException;

}
