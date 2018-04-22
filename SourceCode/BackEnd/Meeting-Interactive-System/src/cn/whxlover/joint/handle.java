package cn.whxlover.joint;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONObject;

import cn.whxlover.dao.*;
import cn.whxlover.dao.Impl.addFriendsDaoImpl;
import cn.whxlover.dao.Impl.friendsListDaoImpl;
import cn.whxlover.dao.Impl.obtainDaoImpl;
import cn.whxlover.dao.Impl.searchDaoImpl;
import cn.whxlover.domain.user;
import cn.whxlover.service.registerSer;
import cn.whxlover.service.impl.registerSerImpl;
import meeting.communicate.MessageSender;
import cn.whxlover.dao.Impl.*;

public class handle {

	public static void handlemess(JSONObject mess) throws IOException, SQLException {
		String operation = mess.getString("operation");
		if (operation.equals("register")) {
			register(mess);	
		} else if (operation.equals("getFriendsInfo")) {
			ArrayList<String> users = new ArrayList<>();
			users.add(mess.getString("userId"));
			MessageSender.sendMessage(getFriendsInfo(mess), users);
		}else if(operation.equals("addFriend")) {
			ArrayList<String> users = new ArrayList<>();
			users.add(mess.getString("userId"));
			MessageSender.sendMessage(addFriend(mess), users);
		}else if(operation.equals("edit")) {
			ArrayList<String> users = new ArrayList<>();
			users.add(mess.getString("userId"));
			MessageSender.sendMessage(edit(mess), users);			
		}else if(operation.equals("search")) {
			ArrayList<String> users = new ArrayList<>();
			users.add(mess.getString("userId"));
			MessageSender.sendMessage(search(mess), users);	
		}
	}
	//给会议模块标签
	public static JSONObject obtain(int userid) throws SQLException {
		JSONObject json = new JSONObject();
		user user = new user();
		String label1;
		String label2;
		obtainDaoImpl obtainDao = new obtainDaoImpl();
		user = obtainDao.obtain(userid);
		json.put("label1", user.getLabel1());
		json.put("label2", user.getLabel2());
		return json;
	}
	//编辑信息
	private static JSONObject edit(JSONObject mess) throws SQLException {
		int issucc;
		JSONObject json = new JSONObject();
		String userid = mess.getString("userid");
		int uid = Integer.parseInt(userid);
		String image = mess.getString("image");
		String sign = mess.getString("sign");
		String department = mess.getString("department");
		
		edit editclass = new edit();
		issucc = editclass.edit(uid,image,sign,department);
		if(issucc>0) {
			json.put("result", "success");
		}else {
			json.put("result", "error");
		}

		return json;
	}
	//添加好友
	private static JSONObject addFriend(JSONObject mess) throws SQLException {
		Boolean is = null;
		String me = null;
		String uid = mess.getString("userid");
		String fid = mess.getString("friendid");
		addFriendsDaoImpl add = new addFriendsDaoImpl();
		is = add.addFriend(uid, fid);
		JSONObject mess1 = new JSONObject();
		if(is) {
			me = "success";
			mess1.put("result", me);
		}else {
			me="error";
			mess1.put("result", me);
		}
		
		return mess1;
	}
	//查询好友
	private static JSONObject search(JSONObject mess) throws SQLException {
		JSONObject json = new JSONObject();
		user user = new user();
		String username = mess.getString("username");
		searchDaoImpl searchDaoImpl = new searchDaoImpl();
		user = searchDaoImpl.searchuser(username);
		if(user!=null) {
			json.put("result", "success");
			json.put("userId", user.getUid());
			json.put("username", user.getUsername());
		}else {
			json.put("result", "error");
		}
		return json;
	}
	//随机获取好友的id
	public static int FriendRandom(int userid) throws SQLException {
		int random;
		int x = 0;
		
		List<user> user = null;
		ArrayList<Integer> id = new ArrayList<>();
		id.add(x);
		friendsListDao friends = new friendsListDaoImpl();
		user = friends.friendsListinfo(userid);
		for (user user2 : user) {	
			x = user2.getUid();			
			id.add(x);			
		}
		int index=(int)(Math.random()*id.size());
		random = id.get(index);
		return random;
	}
	//获取好友信息
	private static JSONObject getFriendsInfo(JSONObject mess) throws SQLException {
		List<user> user = null;
 		JSONObject json = new JSONObject();
 		JSONObject json2 = new JSONObject();
		String Uid = mess.getString("userId");
		int uid = Integer.parseInt(Uid);
		json.put("result", "success");
		//��ѯ����
		friendsListDao friends = new friendsListDaoImpl();
		user = friends.friendsListinfo(uid);
		
		json.append("friendsData", json2);
		//����user
		for (user user2 : user) {	
			JSONObject json3 = new JSONObject();
			json3.put("userId", user2.getUid());
			json3.put("userName", user2.getUsername());
			json3.put("personSign", user2.getSign());
			//base64����
			String image = user2.getImage();
			byte[] src = image.toString().getBytes();
			String base64Str = Base64.getEncoder().encodeToString(src);
			json3.put("avatar", base64Str);
			json2.append("friends", json3);
		}
		byte[] src = json2.toString().getBytes();
		String base64json2 = Base64.getEncoder().encodeToString(src);
		json.put("friendsData", base64json2);
		return json;
	}
	
	//注册
	private static void register(JSONObject mess) throws IOException, SQLException {

		JSONObject json = new JSONObject();
		JSONObject pa = mess.getJSONObject("parameter");
		Map<String, Object> map = new HashMap<String, Object>();
		user user = new user();
		String username = pa.getString("username");
		map.put("username", username);
		String password = pa.getString("password");
		map.put("password", password);
		String Email = pa.getString("email");
		String label1 = pa.getString("label1");
		String label2 = pa.getString("label2");
		map.put("label1", label1);
		map.put("label2", label2);
		map.put("email", Email);
		map.put("image", "");
		map.put("sign", "");
		map.put("department", "");
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		map.put("token", uuid);
		System.out.println(map.get("email"));
		searchDao searchDao = new searchDaoImpl();
		String uid = null;
		// ��װ
		try {
			BeanUtils.populate(user, map);
			System.out.println(user.getEmail());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		// ������
		registerSer registerSer = new registerSerImpl();
		int issuccess = 0;
		try {
			issuccess = registerSer.register(user);
			uid = searchDao.searchid(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(uid);
		//��ѯ�û���id
		if (issuccess > 0) {
			json.put("result", "success");
			json.put("userId", uid);
			json.put("token", uuid);
		}else {
			json.put("result", "false");
			json.put("message", "用户名不能重复");
		}
		ArrayList<String> targetUsers = new ArrayList<>();
		targetUsers.add(mess.getString("sessid"));
		MessageSender.sendMessage(json, targetUsers);
	}

	// ��½����
	public static JSONObject loginoperation(JSONObject mess) {
		JSONObject json = new JSONObject();
		JSONObject parameter = mess.getJSONObject("parameter");
		String token = parameter.getString("token");
		String userid = parameter.getString("userId");
		int uid = Integer.parseInt(userid);
		// ���õ�½����
		Boolean is = joint_login.UserVerifyToken(uid, token);
		String newtoken = joint_login.setLoginToken(uid);
		if (is) {
			json.put("result", "success");
			json.put("token", newtoken);
		} else {
			json.put("result", "error");
			json.put("error", "invalidToken");
		}
		return json;
	}

	// ע������
	public static JSONObject logout(JSONObject mess) {
		JSONObject json = new JSONObject();
		Boolean is = null;
		if (is) {
			json.put("result", "success");
		} else {
			json.put("result", "error");
			json.put("result", "notLogin");
		}
		return null;
	}
	//
}
