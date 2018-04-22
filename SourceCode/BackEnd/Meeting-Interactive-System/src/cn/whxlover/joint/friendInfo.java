package cn.whxlover.joint;

import java.sql.SQLException;

import cn.whxlover.domain.friends;
import cn.whxlover.domain.user;
import cn.whxlover.service.friendinfo;
import cn.whxlover.service.impl.friendinfoImpl;

/**
 * 
 * @author haixue Wang
 * �˷���������ȡ���ѵ�״̬
 */
public class friendInfo {
	public user friendinfoJoin(int fid) {
		friendinfo friendinfo = new friendinfoImpl();
		user user = null;
		try {
			user =  friendinfo.friendinfomation(fid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
}
