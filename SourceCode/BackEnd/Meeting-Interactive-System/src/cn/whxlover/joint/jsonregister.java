package cn.whxlover.joint;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONObject;

import cn.whxlover.domain.user;
import cn.whxlover.service.registerSer;
import cn.whxlover.service.impl.registerSerImpl;

/**
 * 
 * @author haixue Wang
 *ע��
 */
public class jsonregister {
	public Boolean register(JSONObject mess) {
		
		String caozuo = mess.getString("op");
		JSONObject message = mess.getJSONObject("pa");
		JSONObject oo= new JSONObject();
		
		Map<String, Object> map = new HashMap<String, Object>();
		user user = new user();
		//��������
		String uid = mess.getString("uid");
		map.put("uid", uid);
		String username = mess.getString("username");
		map.put("username", username);
		String password = mess.getString("password");
		map.put("password", password);
		String Email = mess.getString("Email");
		map.put("Email", Email);
		String image = mess.getString("image");
		map.put("image", image);
		String sign = mess.getString("sign");
		map.put("sign", sign);
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		map.put("token", uuid);
		try {
			BeanUtils.populate(user, map);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//������
		registerSer registerSer = new registerSerImpl();
		
		int issuccess = 0;
		try {
			issuccess = registerSer.register(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(issuccess>0) {
			return true;
		}else {
			return false;
		}
	}
}
