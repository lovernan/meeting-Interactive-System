package priv.wrebuild.MFunction;

import java.util.Random;

import org.json.JSONObject;

import priv.wrebuild.DAO.DAO;
import priv.wrebuild.Mcontroller.SendJson;
import sun.security.action.PutAllAction;

public class MRecommend {
	/**
	 * 会议智能推荐
	 * @param pmess
	 * @return
	 */
	public JSONObject mRecommend(JSONObject pmess) {
		int u_id=pmess.getInt("u_id");
		//String label1=handle.obtain(u_id).getString("label1");
		//String label2=handle.obtain(u_id).getString("label2");
		//
		//String addsql="(meeting.mLabel='"+label1+"' or meeting.mLabel='"+label2+"') "
		//+"and meeting.s_time is null and meeting_user.cu_id!="+u_id;
		//筛选没有u_id的会议未开始的会议
		//int[] ms=new DAO().outputList(addsql,"meeting_user.m_id","meeting,meeting_user");
		
		//Random random=new Random();
		//int m=ms[random.nextInt(ms.length)];
		JSONObject mRecommend=new SendJson().successJson("mRecommend");
		mRecommend.put("m_id", "");
		return mRecommend;
	
	}
	
	/**
	 * 好友会议推荐
	 * @param pmess
	 * @return
	 */
	
	public JSONObject mFriendRecommend(JSONObject pmess) {
		int u_id=pmess.getInt("u_id");
		// int fu_id=handle.FriendRandom(u_id);
		//String addsql="meeting.s_time is null and meeting_user.cu_id="+fu_id;
		//筛选没有u_id的会议未开始的会议
		//int[] ms=new DAO().outputList(addsql,"meeting_user.m_id","meeting,meeting_user");
		
		//Random random=new Random();
		//int m=ms[random.nextInt(ms.length)];
		JSONObject mFriendRecommend=new SendJson().successJson("mFriendRecommend");
		mFriendRecommend.put("m_id", "");
		return mFriendRecommend;
	}
}
