package cn.whxlover.dao.Impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import cn.whxlover.domain.*;
import cn.whxlover.dao.isFriend;
import cn.whxlover.utils.DataSourceUtils;

public class isFriendImpl implements isFriend {

	@Override
	public Boolean isFriend(int userid, int friendid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from friends where uid=? and fid = ?";
		friends query = runner.query(sql, new BeanHandler<friends>(friends.class), userid,friendid);
		if(query!=null) {
			return true;
		}else {
			return false;
		}
		
	}

}
