package cn.whxlover.joint;


import cn.whxlover.service.loginSer;

import java.sql.SQLException;

import cn.whxlover.dao.*;
import cn.whxlover.dao.Impl.*;


/**
 * 
 * @author haixue Wang
 *�ж��Ƿ�Ϊ����
 */
public class jointIsFriend {
	public Boolean isFriend(int userid,int friendid) {
		isFriend isFriend =new isFriendImpl();
		Boolean succ= null;
		try {
			succ= isFriend.isFriend(userid,friendid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return succ;
	}
}
