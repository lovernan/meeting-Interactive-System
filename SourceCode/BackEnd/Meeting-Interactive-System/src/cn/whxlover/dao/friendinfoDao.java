package cn.whxlover.dao;

import java.sql.SQLException;

import cn.whxlover.domain.user;

public interface friendinfoDao {

	user friendinfo(int uid) throws SQLException;

}
