package priv.wrebuild.Mcontroller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;

import meeting.communicate.MessageSender;
import priv.wrebuild.MFunction.MBulletScreen;
import priv.wrebuild.MFunction.MGrade;
import priv.wrebuild.MFunction.MInvite;
import priv.wrebuild.MFunction.MRecommend;
import priv.wrebuild.MFunction.MRegister;
import priv.wrebuild.MFunction.MSetAdmin;
import priv.wrebuild.MFunction.MSign;
import priv.wrebuild.MFunction.MSweepstake;
import priv.wrebuild.MFunction.MVote;
import priv.wrebuild.MFunction.MeetingBegin;
import priv.wrebuild.MFunction.MeetingEnd;
import priv.wrebuild.MFunction.MeetingStatus;
import priv.wrebuild.MFunction.Mjoin;

/**
 * 该类为会议的主控制类
 * @author Wrebuild
 *
 */
public class MController {
	
	/**
	 * 此方法处理JSON消息
	 * @param mess
	 * @throws SQLException 
	 * @throws IOException 
	 */
	
	public static void cJsonMess(JSONObject mess) throws IOException, SQLException {
		
	String operate=mess.getString("operation");
	
	System.out.println(operate);
	
	//System.out.println(operation);
	//System.out.println(mess.getJSONObject("parameter").toString());
	
	if(operate!=null) {
		
		//该操作为注册操作
		if(operate.equals("mRegister")) {
			JSONObject paras=mess.getJSONObject("parameter");
			//获取会议信息
			JSONObject smess=new MRegister().mRegister(paras);
			System.out.println(smess.toString());
			ArrayList<String> targetUser=new ArrayList<String>();
			targetUser.add(paras.getInt("mCreator_id")+"");
			for(String s:targetUser) {
				System.out.println(s);
			}
			//返回信息
			 
				MessageSender.sendMessage(smess,targetUser);
		
			}
		
		//该操作为获取会议签到二维码的操作
		if(operate.equals("mSignID")) {
			JSONObject paras=mess.getJSONObject("parameter");
			JSONObject smess=new MSign().smSignID(paras);
			System.out.println(smess.toString());
			ArrayList<String> targetUser=new ArrayList<String>();
			targetUser.add(paras.getInt("u_id")+"");
			//返回信息
			 
				MessageSender.sendMessage(smess,targetUser);
			
			//new MSendJson().mSign(mess.getInt("parameter"));
			//System.out.println(new MSendJson().mSign(mess.getInt("parameter")).toString());
		}
		
		//该操作为签到操作
		if(operate.equals("mSign")) {
			JSONObject paras=mess.getJSONObject("parameter");
			
			JSONObject smess=new MSign().mSign(paras);
			//System.out.println(smess.toString());
			ArrayList<String> targetUser=new ArrayList<String>();
			targetUser.add(paras.getInt("u_id")+"");
			//返回信息
			
				MessageSender.sendMessage(smess,targetUser);
		
			
		}
		
		//该操作为邀请操作
		if(operate.equals("mInvite")) {
			JSONObject paras=mess.getJSONObject("parameter");
			JSONObject smess=new MInvite().mInvite(mess.getJSONObject("parameter"));
			System.out.println(smess.toString());
			ArrayList<String> targetUser=new ArrayList<String>();
			targetUser.add(paras.getInt("iu_id")+"");
			for (String s:targetUser) {
				System.out.println(s);
			}
			//返回信息
			
				MessageSender.sendMessage(smess,targetUser);
			
		}
		
		//该操作为参加操作
		if(operate.equals("mJoin")) {
			
			JSONObject paras=mess.getJSONObject("parameter");
			JSONObject smess=new Mjoin().mJoin(paras);
			System.out.println(smess.toString());
			//json对象
			JSONObject sendUser=smess.getJSONObject("SendUser");
			JSONObject sendAdmin=smess.getJSONObject("SendAdminJoin");
			//发送用户
			ArrayList<String> user=new ArrayList<String>();
			user.add(paras.getInt("u_id")+"");
			
			//
			
			if(smess.getString("result").equals(" success")) {
				System.out.println("success");
				ArrayList<String> admins=new MSetAdmin().adminsId(paras);
				for (String s:admins) {
					System.out.println(s);
				}
				for (String s:user) {
					System.out.println(s);
				}
					MessageSender.sendMessage(sendUser,user);
				
				MessageSender.sendMessage(sendAdmin,admins);
				
			}else {
				System.out.println("unsuccess");

				for (String s:user) {
					System.out.println(s);
				}
				MessageSender.sendMessage(smess,user);
			}
		
					
		}
				
		//该操作为设置管理员操作
		if(operate.equals("mSetAdmin")) {
			JSONObject paras=mess.getJSONObject("parameter");
			JSONObject smess=new MSetAdmin().mSetAdmin(paras);
			
			ArrayList<String> targetUser=new ArrayList<String>();
			targetUser.add(paras.getInt("u_id")+"");
			//返回信息
			
				 MessageSender.sendMessage(smess,targetUser);
		
			}
		
		//该操作为查看会议信息操作
				if(operate.equals("mInformation")) {
					JSONObject paras=mess.getJSONObject("parameter");
					JSONObject smess=new MeetingStatus().mInformation(paras);

					ArrayList<String> targetUser=new ArrayList<String>();
					targetUser.add(paras.getInt("u_id")+"");
					//返回信息
					 
						MessageSender.sendMessage(smess,targetUser);
				
					}
				
				
		//该操作为管理员审核用户参加操作
		if(operate.equals("mAdminReview")) {
			JSONObject paras=mess.getJSONObject("parameter");
			JSONObject smess=new Mjoin().mJionResult(paras);
			System.out.println(smess);
			ArrayList<String> targetUser=new ArrayList<String>();
			targetUser.add(paras.getInt("ju_id")+"");
			//返回信息
			 
				 for (String s:targetUser) {
						System.out.println(s);
					}
				MessageSender.sendMessage(smess,targetUser);
		
			}

		}
				
		//该操作为管理员申请投票操作
		if(operate.equals("adminVote")) {
			JSONObject paras=mess.getJSONObject("parameter");
			JSONObject smess=new MVote().adminVote(paras);
			System.out.println(smess);
			ArrayList<String> targetUser=new ArrayList<String>();
			targetUser.add(paras.getInt("u_id")+"");
			//返回信息
			
				MessageSender.sendMessage(smess,targetUser);
		
			}
		
		//该操作为用户投票操作
		if(operate.equals("userVote")) {
			JSONObject paras=mess.getJSONObject("parameter");
			JSONObject smess=new MVote().userVote(paras);

			ArrayList<String> targetUser=new ArrayList<String>();
			targetUser.add(paras.getInt("u_id")+"");
			//返回信息
			
				MessageSender.sendMessage(smess,targetUser);
			
			}
		
		//该操作为查看投票结果操作
		if(operate.equals("voteResult")) {
			JSONObject paras=mess.getJSONObject("parameter");
			JSONObject smess=new MVote().voteResult(paras);
			System.out.println(smess.toString());
			ArrayList<String> targetUser=new ArrayList<String>();
			targetUser.add(paras.getInt("u_id")+"");
			//返回信息
			 
				MessageSender.sendMessage(smess,targetUser);
			
			}
		
		//该操作为弹幕操作
		if(operate.equals("mBulletScreen")) {
			JSONObject paras=mess.getJSONObject("parameter");
			JSONObject smess=new MBulletScreen().mBulletScreen(paras);
			System.out.println(mess.toString());
			ArrayList<String> targetUser=new ArrayList<String>();
			targetUser.add(paras.getInt("u_id")+"");
			//返回信息
			
				MessageSender.sendMessage(smess,targetUser);
			
			}
		
		//该操作为抽奖操作
		if(operate.equals("mSweepstake")) {
			
			JSONObject paras=mess.getJSONObject("parameter");
			JSONObject smess=new MSweepstake().mSweepstake(paras);
			System.out.println(smess);
			ArrayList<String> targetUser=new ArrayList<String>();
			targetUser.add(paras.getInt("u_id")+"");
			//返回信息
			
				MessageSender.sendMessage(smess,targetUser);
			
			}
		
		//该操作为管理员申请评分操作
		if(operate.equals("adminGrade")) {
			JSONObject paras=mess.getJSONObject("parameter");
			JSONObject smess=new MGrade().adminGrade(paras);
			System.out.println(smess.toString());
			ArrayList<String> targetUser=new ArrayList<String>();
			targetUser.add(paras.getInt("u_id")+"");
			//返回信息
			
				MessageSender.sendMessage(smess,targetUser);
		
		}
		
		//该操作为用户评分操作
		if(operate.equals("userGrade")) {
			JSONObject paras=mess.getJSONObject("parameter");
			JSONObject smess=new MGrade().userGrade(paras);
			System.out.println(smess.toString());
			ArrayList<String> targetUser=new ArrayList<String>();
			targetUser.add(paras.getInt("u_id")+"");
			//返回信息
			 
				MessageSender.sendMessage(smess,targetUser);
		
		}
				
		
		//该操作为查看评分结果操作
		if(operate.equals("gradeResult")) {
			JSONObject paras=mess.getJSONObject("parameter");
			JSONObject smess=new MGrade().gradeResult(paras);
			System.out.println(smess.toString());
			ArrayList<String> targetUser=new ArrayList<String>();
			targetUser.add(paras.getInt("u_id")+"");
			//返回信息
			
				MessageSender.sendMessage(smess,targetUser);
		
			}
		
		//该操作为结束操作
		if(operate.equals("meetingEnd")) {
			
			JSONObject paras=mess.getJSONObject("parameter");
			JSONObject smess=new MeetingEnd().meetingEnd(paras);	
			System.out.println(smess.toString());
			ArrayList<String> targetUser=new ArrayList<String>();
			targetUser.add(paras.getInt("u_id")+"");
			//返回信息
			 
				MessageSender.sendMessage(smess,targetUser);
		
			}
		
	
	//该操作为会议开始操作
	if(operate.equals("meetingBegin")) {
		JSONObject paras=mess.getJSONObject("parameter");
		JSONObject smess=new MeetingBegin().meetingBegin(paras);
		System.out.println(smess.toString());
		ArrayList<String> targetUser=new ArrayList<String>();
		targetUser.add(paras.getInt("u_id")+"");
		//返回信息
		
			MessageSender.sendMessage(smess,targetUser);
	
		}
	

	//该操作为智能会议推荐操作
	if(operate.equals("mRecommend")) {
		JSONObject paras=mess.getJSONObject("parameter");
		JSONObject smess=new MRecommend().mRecommend(mess);
		System.out.println(smess.toString());
		ArrayList<String> targetUser=new ArrayList<String>();
		targetUser.add(paras.getInt("u_id")+"");
		//返回信息
		
			MessageSender.sendMessage(smess,targetUser);
		}
	
	//该操作为好友会议推荐操作
	if(operate.equals("mFriendRecommend")) {
		JSONObject paras=mess.getJSONObject("parameter");
		JSONObject smess=new MRecommend().mFriendRecommend(mess);
		System.out.println(smess.toString());
		ArrayList<String> targetUser=new ArrayList<String>();
		targetUser.add(paras.getInt("u_id")+"");
		//返回信息
		
			MessageSender.sendMessage(smess,targetUser);
		}
	
	//该操作为获取会议标签操作
	if(operate.equals("mLabel")) {
		JSONObject smess=new SendJson().successJson("mLabel");
		String[] l=new String[]{"专业性会议","商务性会议","联谊性会议","决策性会议","工作性会议"};
		smess.put("paras",l);
		ArrayList<String> targetUser=new ArrayList<String>();
		targetUser.add(mess.getInt("u_id")+"");
		//返回信息
		
			MessageSender.sendMessage(smess,targetUser);
		
		}


	//该操作为获取所有会议信息操作
	if(operate.equals("getMeetingsInformation")) {
		
		JSONObject smess=new MeetingStatus().getAllMeeting();
		System.out.println(smess.toString());
		ArrayList<String> targetUser=new ArrayList<String>();
		targetUser.add(mess.getInt("u_id")+"");
		
		//返回信息
		MessageSender.sendMessage(smess,targetUser);
	
		}
	}
	
	public static void main(String[] args) throws IOException, SQLException {
		JSONObject a = new JSONObject();
		a.put("operation", "getMeetingsInformation");
		a.put("u_id", 33);
		MController.cJsonMess(a);
	}
	
	
}
