package cn.whxlover.service;

import java.sql.SQLException;
import java.util.List;

import cn.whxlover.domain.friends;

public interface friendsListSer {

	List<friends> friendsList(String uid) throws SQLException;

}
