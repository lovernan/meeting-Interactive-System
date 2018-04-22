package priv.wrebuild.MFunction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.json.JSONObject;

import priv.wrebuild.DAO.DAO;
import priv.wrebuild.Mcontroller.SendJson;

/**
 * 该类为会议注册操作
 * @author Wrebuild
 *
 */

public class MRegister {
	/**
	 * 注册会议操作
	 * @param pmess 
	 */
	public JSONObject mRegister(JSONObject pmess) {
		//用UUID生成会议标识符
		String msign_id = UUID.randomUUID().toString();
		//设置日期格式,获取当前系统时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String c_time=df.format(new Date());
		//获取pMess中的各个变量
		String m_title=pmess.getString("m_title");
		String b_time=pmess.getString("b_time");
		String m_place=pmess.getString("m_place");
		String m_content=pmess.getString("m_content");
		int mCreator_id=pmess.getInt("mCreator_id");
		String mLabel=pmess.getString("mLabel");
		//将会议信息写入数据库中		
		int m_id=new DAO().addMeeting(msign_id,m_title, c_time, b_time, m_place, m_content, mCreator_id,mLabel);
		//System.out.println(meeting.getM_id());
		//将m_id和creator_id存入关系表
		new DAO().addMeeting_User(m_id, mCreator_id);
		//将mCreator_id设置为管理员
		new DAO().updateMuByID(mCreator_id,m_id,"admin",mCreator_id+"");
		JSONObject sMRegister;
		//para赋值
		//sMRegister=new SendJson().successJson("mRegister");
		if(m_id+""!=null) {
			sMRegister=new SendJson().successJson("mRegister");
			sMRegister.put("m_id", m_id);
			
		}else {
			sMRegister=new SendJson().errorJson("mRegister");
		}
		return sMRegister;
	}
	
}
