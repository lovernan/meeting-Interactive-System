package priv.wrebuild.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;

import org.json.JSONObject;

import com.mysql.fabric.xmlrpc.base.Array;

import sun.text.resources.cldr.om.FormatData_om;

/**
 * 该类为数据库处理
 * 
 * @author Wrebuild
 *
 */


public class DAO {
	/**
	 * 连接数据库所需的各种变量
	 */
	String driver = "com.mysql.jdbc.Driver";
	
	String url = "jdbc:mysql://localhost:3306/meeting";
	
	Connection con = null;
	
	Statement cmd = null;
	
	PreparedStatement pstmt = null;
	
	ResultSet rs = null;
	
	/**
	 * 此方法为构造方法，创建数据库连接
	 * @throws ClassNotFoundException
	 * @throws SQLException 
	 */ 
	
	public DAO(){
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,"root","Iloveyou151");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 此方法创建会议记录
	 * @param m Meeting对象
	 * @throws SQLException 
	 */
	public int addMeeting(String msign_id,String m_title,String c_time,String b_time,String m_place,String m_content,int mCreator_id,String mLabel) {
		//声明m变量，用于存放m_id;
		int m = 0;
		try {
			String sql="insert into meeting values(default,default,"
					+ "default,default,default,?,?,?,?,?,?,?,?)";
			pstmt=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, msign_id);
			pstmt.setString(2, m_title);
			pstmt.setString(3, c_time);
			pstmt.setString(4, b_time);
			pstmt.setString(5, m_place);
			pstmt.setString(6, m_content);
			pstmt.setInt(7, mCreator_id);
			pstmt.setString(8, mLabel);
			System.out.println(sql);
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			//获取主键m_id
            if(rs.next())
            {
               m=rs.getInt(1);
            }
            pstmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}
	
	/**
	 * 此方法增加关系表记录
	 * @param m_id
	 * @param u_id
	 */
	public void addMeeting_User(int m_id,int u_id) {
		try {
			String sql="insert into meeting_user values(?,?,default,default,default,default)";
			//System.out.println(sql);
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, m_id);
			pstmt.setInt(2,u_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 此方法根据m_id值删除会议
	 * @param m_id meeting的主键
	 * @throws SQLException 
	 */
		public void deleteMeetingByID(int m_id ) throws SQLException{
			cmd=con.createStatement();
			String sql = "delete from meeting where m_id="+m_id;
			cmd.executeUpdate(sql);
			cmd.close();
		}
		
		/**
		 * 此方法根据m_id更改字段的值
		 * @param m_id meeting的主键
		 * @param list 要更改值的字段
		 * @param value 要更改的值
		 * @throws SQLException 
		 */
		public void updateMeetingByID(int m_id,String list,String value) {
			try {
			cmd=con.createStatement();
			String sql = "update meeting set "+list+"='"+value+"' where m_id="+m_id;
			System.out.println(sql);
			cmd.executeUpdate(sql);
			cmd.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 此方法根据u_id和m_id更改表的值
		 * @param m_id
		 * @param list
		 * @param value
		 */
		public void updateMuByID(int u_id,int m_id,String list,String value) {
			try {
			cmd=con.createStatement();
			String sql = "update meeting_user set "+list+"='"+value+"' where m_id="+m_id+" and cu_id="+u_id;
			System.out.println(sql);
			cmd.executeUpdate(sql);
			cmd.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 此方法根据m_id查询字段的值，同时也有验证的效果
		 * @param m_id
		 * @param list
		 * @return
		 * @throws SQLException 
		 */
		public String queryTableByID(int m_id,String list,String table)  {
			String record=null;
			try {
				cmd=con.createStatement();
		
			String sql ="select "+list+" from "+table+" where m_id="+m_id;
			System.out.println(sql);
			rs=cmd.executeQuery(sql);
			while(rs.next()) {
			record=rs.getString(1);
			}
			cmd.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			return record;
		}
		
		/**
		 * 关系表验证
		 * @param m_id
		 * @param list
		 * @param table
		 * @return
		 */
		public String queryMuByID(int m_id,int u_id,String list)  {
			String record=null;
			try {
				cmd=con.createStatement();
		
			String sql ="select "+list+" from meeting_user where m_id="+m_id+" and cu_id="+u_id;
			System.out.println(sql);
			rs=cmd.executeQuery(sql);
			while(rs.next()) {
			record=rs.getString(1);
			}
			cmd.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			return record;
		}
		
		/**
		 * 输出表中字段的值
		 * @param list
		 * @return
		 * @throws SQLException 
		 */
		public int[] outputList(String addsql,String list,String table) {
			int i=0;int[] n=null;
			
				try {
					cmd=con.createStatement();
				String sql="select "+list+" from "+table+" "+addsql;
				System.out.println(sql);
				rs=cmd.executeQuery(sql);
				rs.last();n=new int[rs.getRow()];
				rs.first();
				do{
					n[i]=rs.getInt(1);
					i++;
				}while(rs.next()) ;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			return n;
		}
		
		
		/**
		 * 查看会议状态
		 * @param addSql
		 * @return
		 */
		public int listStatus(String addSql) {
			int m=0;
			try {
				
				cmd=con.createStatement();
		
			String sql ="select count(*) from "+addSql;
			System.out.println(sql);
			rs=cmd.executeQuery(sql);
			while(rs.next()) {
				//System.out.println(rs.getInt(1));
				m=rs.getInt(1);
			}
			cmd.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			return m;
			
		}
}
