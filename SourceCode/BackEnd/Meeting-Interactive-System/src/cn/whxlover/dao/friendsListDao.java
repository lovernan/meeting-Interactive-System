package cn.whxlover.dao;

import java.sql.SQLException;
import java.util.List;

import cn.whxlover.domain.*;

public interface friendsListDao {

	List<friends> friendsList(String uid) throws SQLException;

	List<user> friendsListinfo(int uid) throws SQLException;

	

}
