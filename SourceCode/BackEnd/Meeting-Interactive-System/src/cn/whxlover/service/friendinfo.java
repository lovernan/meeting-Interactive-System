package cn.whxlover.service;

import java.sql.SQLException;

import cn.whxlover.domain.friends;
import cn.whxlover.domain.user;

public interface friendinfo {

	user friendinfomation(int fid) throws SQLException;

}
