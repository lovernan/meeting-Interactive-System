package priv.wrebuild.MFunction;

import org.json.JSONObject;

import priv.wrebuild.DAO.DAO;
import priv.wrebuild.Mcontroller.SendJson;
/**
 * 该类为用户投票操作
 * @author Wrebuild
 *
 */

public class MVote {
	
	/**
	 * 管理员判断操作
	 * @param pmess
	 * 
	 */
	public JSONObject adminVote(JSONObject pmess) {
		
		int m_id=pmess.getInt("m_id");
		int u_id=pmess.getInt("u_id");
		JSONObject AdminVote;
		if(new DAO().queryMuByID(m_id,u_id, "admin")!=null) {
			new DAO().updateMeetingByID(m_id,"vContent", pmess.getString("vContent"));
			AdminVote=new SendJson().successJson("adminVote");
		}else {
			AdminVote=new SendJson().errorJson("adminVote");
			AdminVote.put("error", "user isn't admin");
		}
		return AdminVote;
	}
	
	/**
	 * 用户投票操作
	 * @param pmess
	 */
	public JSONObject userVote(JSONObject pmess) {
		int m_id=pmess.getInt("m_id");
		int u_id=pmess.getInt("u_id");
		JSONObject UserVote;
		//用户的签到
		if(new DAO().queryMuByID(m_id, u_id, "sign")!=null) {
			new DAO().updateMuByID(u_id, m_id, "vote", pmess.getString("vResult"));
			UserVote=new SendJson().successJson("UserVote");
		}else {
			UserVote=new SendJson().errorJson("UserVote");
			UserVote.put("error", "user does't join meeting");
		}
		return UserVote;
	}
	
	/**
	 * 查看投票结果
	 * @param pmess
	 */
	public JSONObject voteResult(JSONObject pmess) {
		JSONObject VoteResult;
		int u_id=pmess.getInt("u_id");
		int m_id=pmess.getInt("m_id");
		VoteResult=new SendJson().successJson("voteResult");
		int A=new DAO().listStatus("meeting_user where m_id="+m_id+" and vote='A'");
		int B=new DAO().listStatus("meeting_user where m_id="+m_id+" and vote='B'");
		int C=new DAO().listStatus("meeting_user where m_id="+m_id+" and vote='C'");
		int D=new DAO().listStatus("meeting_user where m_id="+m_id+" and vote='D'");
		int E=new DAO().listStatus("meeting_user where m_id="+m_id+" and vote='E'");
		JSONObject para=new JSONObject();
		para.put("A", A);
		para.put("B", B);
		para.put("C", C);
		para.put("D", D);
		para.put("E", E);
		VoteResult.put("parameter", para);
		/*
			String[] all=new DAO().outputSList(m_id, "vote", "meeting_user");
			String[] A=new DAO().outputSList(m_id, "vote=A", "meeting_user");
			String[] B=new DAO().outputSList(m_id, "vote=B", "meeting_user");
			String[] C=new DAO().outputSList(m_id, "vote=C", "meeting_user");
			String[] D=new DAO().outputSList(m_id, "vote=D", "meeting_user");
			VoteResult=new SendJson().successJson("voteResult");
			JSONObject para=new JSONObject();
			para.put("all", all);
			para.put("A", A);
			para.put("B", B);
			para.put("C", C);
			para.put("D", D);
			VoteResult.put("parameter", pmess);
			VoteResult.put("resultPara", para);*/
		
		return VoteResult;
	}	
}
