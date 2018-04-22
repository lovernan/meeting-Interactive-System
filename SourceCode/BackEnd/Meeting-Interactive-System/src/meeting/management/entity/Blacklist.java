package meeting.management.entity;

import java.util.ArrayList;

/**
 * 实体类，用于保存黑名单。
 * 
 * @author Shuolin Yang
 *
 */
public class Blacklist {
	
	/**
	 * 用于保存用户ID的列表。
	 */
	private ArrayList<String> useridList = null;
	
	/**
	 * 用于保存黑名单用户被禁时间的列表。
	 */
	private ArrayList<String> startTimeList = null;
	
	/**
	 * 构造函数，初始化数据.
	 */
	public Blacklist() {
		this.useridList = new ArrayList<>();
		this.startTimeList = new ArrayList<>();
	}
	
	/**
	 * 用于向对象中添加一个黑名单用户。
	 * 
	 * @param userId 用户ID
	 * @param startTime 用户被禁时间字符串
	 */
	public void setUser(String userId, String startTime) {
		useridList.add(userId);
		startTimeList.add(startTime);
	}
	
	/**
	 * 返回当前用户数量。
	 * 
	 * @return 用户数量
	 */
	public int getLength() {
		return useridList.size();
	}
	
	/**
	 * 用于返回在index位置的用户ID。
	 * 
	 * @param index 用户ID的索引
	 * @return 用户ID
	 */
	public String getUserId(int index) {
		return useridList.get(index);
	}
	
	/**
	 * 用于返回在index位置的被禁时间。
	 * 
	 * @param index 时间的索引
	 * @return 时间字符串
	 */
	public String getStartTime(int index) {
		return startTimeList.get(index);
	}
}
