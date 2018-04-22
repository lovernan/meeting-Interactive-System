package cn.whxlover.dao.Impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import cn.whxlover.utils.DataSourceUtils;

public class edit {
	//编辑操作
	public int edit(int uid, String image, String sign, String department) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set image = ?,sign= ?,department = ? where uid = ?";
		int update = runner.update(sql, image,sign,department,uid);
		return update;
	}


}
