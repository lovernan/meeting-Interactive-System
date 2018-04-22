package cn.whxlover.service.impl;

import java.sql.SQLException;

import cn.whxlover.dao.userloginDao;
import cn.whxlover.dao.Impl.userloginDaoImpl;
import cn.whxlover.domain.user;
import cn.whxlover.service.loginSer;

public class loginSerImpl implements loginSer {

	@Override
	public user userlogin(String username, String password) throws SQLException {
		userloginDao userloginDao = new userloginDaoImpl();
		return userloginDao.userlogin(username,password);
		
	}

	@Override
	//����½��tocken�������ݿ�
	public Boolean loginTocken(String uuid,int userid) throws SQLException {
		// TODO Auto-generated method stub
		userloginDao userloginDao = new userloginDaoImpl();
		return userloginDao.loginTocken(uuid,userid);
		
	}

	@Override
	//�û��ٴε�½ʱ��֤token�Ƿ���ȷ ����Boolean
	public Boolean verifyToken(int userid, String token) throws SQLException {
		// TODO Auto-generated method stub
		userloginDao userloginDao = new userloginDaoImpl();
		return userloginDao.verifytoken(userid,token);
		
	}

	@Override
	//�û��ٴε�½ʱ��֤token�Ƿ���ȷ �����û���Ϣ
	public user userVerifyToken(int userid, String token) throws SQLException {
		
		userloginDao userloginDao = new userloginDaoImpl();
		return userloginDao.userverifytoken(userid,token);
	}

	@Override
	//ͨ���û�����ѯ�û���id
	public user find(String username) throws SQLException {
		userloginDao userloginDao = new userloginDaoImpl();
		return  userloginDao.find(username);
		
	}

	//ͨ���û��������½
		@Override
		public Boolean loginin(int userid, String password) throws SQLException {
			userloginDao userloginDao = new userloginDaoImpl();
			return userloginDao.loginin(userid,password);
		}

}
