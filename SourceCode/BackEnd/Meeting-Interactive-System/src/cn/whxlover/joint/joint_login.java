package cn.whxlover.joint;

import java.sql.SQLException;
import java.util.UUID;


import cn.whxlover.domain.user;
import cn.whxlover.service.loginSer;
import cn.whxlover.service.impl.loginSerImpl;


/**
 * 
 * @author haixue Wang
 * ˵��������ּ�������Ựϵͳ��ɶԽ�
 * �Խӹ��ܣ���½
 *
 */

public class joint_login {
	
	
	//�����´ε�½ʱ��tocken����
	private String Tocken;
	
	//�÷�����ʵʱ�����û���tocken
	public static String setLoginToken(int userid) {
		//����һ������ַ���
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		//��tocken�������ݿ�
		loginSer loginser =new loginSerImpl();
		Boolean succ = null;
		try {
			 succ = loginser.loginTocken(uuid,userid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(succ) {	
			return uuid;
		}else {
			return "error";	
		}
	}
	
	//�÷���������֤Tocken����Ч�� ����boolean
	public static Boolean UserVerifyToken(int userid,String token) {
		loginSer loginser =new loginSerImpl();
		Boolean succ = null;
		try {
			succ = loginser.verifyToken(userid,token);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return succ;
	}
	
	public static String login(int userid,String token) {
		loginSer loginser =new loginSerImpl();
		Boolean succ = null;
		try {
			succ = loginser.verifyToken(userid,token);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(succ) {
			return setLoginToken(userid);
		}else {
			return "error";
		}
	}
	//�÷���������֤Tocken����Ч�� �����û���Ϣ	
	public static user UserVerifyToken2(int userid,String token) {
		loginSer loginser =new loginSerImpl();
		user user = new user();
		try {
			user = loginser.userVerifyToken(userid,token);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return user;
	}

	public static String ReturnUid(String username) {
		loginSer loginser =new loginSerImpl();
		String Uid =null;
		user user = new user();
		int uid = 0;
		try {
			user =  loginser.find(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(user==null) {
			return "error";
		}else {
			
			try {			
				uid =  user.getUid();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			Uid = Integer.toString(uid);
		}
		return Uid;
	}
	public static Boolean loginin(int userid,String password) throws SQLException {
		loginSer loginser =new loginSerImpl();
		Boolean succ = null;
		succ = loginser.loginin(userid,password);
		if(succ) {
			return true;
		}else {
			return false;
		}
	}
	
}
