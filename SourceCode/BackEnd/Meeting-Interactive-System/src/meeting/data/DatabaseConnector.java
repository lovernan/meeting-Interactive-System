package meeting.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 用于连接数据库的类，用于Endpoint存储缓存消息。
 * 
 * @author Shuolin Yang
 *
 */
public class DatabaseConnector {
	
	/**
	 * 用于保存数据库连接的各变量。
	 */
	private String url = "jdbc:mysql://localhost:3306/meeting";
	
	private String table = "co_cached_message";
	
	private String user = "root";
	
	private String password = "Iloveyou151";
	
	private Connection connection = null;
	
	private Statement statement = null;
	
	private ResultSet result = null;
	
	/**
	 * 构造函数，用于连接数据库。
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public DatabaseConnector() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(url, user, password);
	}
	
	/**
	 * 用来存储缓存消息的方法。
	 * 
	 * @param userId 目标用户的ID
	 * @param message 缓存的消息
	 * @return 保存成功返回true，失败返回false
	 * @throws SQLException 
	 */
	public void cacheMessage(String userId, String message) throws SQLException {
		
		statement = connection.createStatement();
		
		// 构造SQL语句
		String sql = "INSERT INTO `" + table + "` VALUES('" + userId + "','" + message + "',default);";
		
		// 插入数据
		statement.executeUpdate(sql);
		
		statement.close();
	}
	
	/**
	 * 返回为给定用户存储的所有临时消息字符串。
	 * 
	 * @param userId 指定用户的userId
	 * @return 存储的消息字符串数组列表
	 * @throws SQLException 
	 */
	public ArrayList<String> getCachedMessage(String userId) throws SQLException {
		
		String sql = null;
		
		// 获取缓存消息
		statement = connection.createStatement();
		
		// 构造SQL语句
		sql = "SELECT `message` FROM `" + table + "` WHERE user_id = '" + userId +"';";
		
		// 查询语句
		result = statement.executeQuery(sql);
		
		// 返回消息数组
		ArrayList<String> message = new ArrayList<>();
		while (result.next()) {
			message.add(result.getString("message"));
		}
		
		result.close();
		statement.close();
		
		// 删除已经使用过的缓存消息
		statement = connection.createStatement();
		
		// 构造SQL语句
		sql = "DELETE FROM `" + table + "` WHERE user_id = '" + userId +"';";
		
		// 执行删除
		statement.executeUpdate(sql);
		
		statement.close();
		
		return message;
	}
}
