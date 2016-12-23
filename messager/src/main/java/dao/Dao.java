package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.glassfish.jersey.servlet.internal.spi.ServletContainerProvider;

import dto.MusicListResponse;

public class Dao implements ServletContextListener{
	// create table ENTRIES ( ID INTEGER PRIMARY KEY,LINK VARCHAR(200), NAME TEXT CHARACTER SET utf8 COLLATE utf8_unicode_ci, OWNER VARCHAR(50), DOWNLOAD_STATUS TINYINT(1), DOWNLOAD_STARTED TIMESTAMP, DOWNLOAD_COMPLETED TIMESTAMP, PATH TEXT CHARACTER SET utf8 COLLATE utf8_unicode_ci);
	// ALTER TABLE ENTRIES MODIFY COLUMN ID INTEGER AUTO_INCREMENT;
	// ALTER TABLE ENTRIES ADD CONSTRAINT constraint1 UNIQUE(LINK)

//	public void contextInitialialized(ServletContextEvent arg0){
//		
//	};
//	
//	public void contextDServletContextListenerestroyed(ServletContextEvent arg0){
//		
//	}
	public Dao() {
		setupConnection();
	}

	private Connection con;

	public void setupConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pi_database", "user", "user");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void createNewEntry(String name, String owner, boolean downloadStatus, long downloadStarted,
			long downloadCompleted, String path, String link) {
		try {
			Timestamp start = new Timestamp(downloadStarted);
			Timestamp completed = new Timestamp(downloadCompleted);
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO ENTRIES(NAME,OWNER,DOWNLOAD_STATUS, DOWNLOAD_STARTED, DOWNLOAD_COMPLETED, PATH, LINK) VALUES (?,?,?,?,?,?,?)");
			ps.setString(1, name);
			ps.setString(2, owner);
			ps.setBoolean(3, downloadStatus);
			ps.setTimestamp(4, start);
			ps.setTimestamp(5, completed);
			ps.setString(6, path);
			ps.setString(7, link);
			int result = ps.executeUpdate();
			System.out.println("entries update with " + result + " rows");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Sql Exception caught: " + e.toString());
			e.printStackTrace();
		}
	}

	public ArrayList<MusicListResponse> getNewEntries(long time, String owner) {
		ArrayList<MusicListResponse> response = new ArrayList<>();
		try {
			PreparedStatement ps = con.prepareStatement(
					"SELECT ID, NAME FROM ENTRIES WHERE OWNER = ? AND DOWNLOAD_STATUS = 1 AND DOWNLOAD_COMPLETED >= ? ");
			ps.setString(1, owner);
			ps.setLong(2, time);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				MusicListResponse unitResponse = new MusicListResponse();
				unitResponse.setId(rs.getInt("ID"));
				unitResponse.setName(rs.getString("NAME"));
				response.add(unitResponse);
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}

		return response;
	}

	/*
	 * public static void main(String args[]){ Dao daoObj = new Dao();
	 * daoObj.setupConnection(); daoObj.createNewEntry("asdf1", "palash", false,
	 * System.currentTimeMillis(), System.currentTimeMillis(), "/",
	 * "www.youtube.com");
	 * 
	 * }
	 */
	public void finalize() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error while closing db  connecction " + e.toString());
			e.printStackTrace();
		}
	}

	public String getPathById(Integer id){
		// TODO Auto-generated method stub
		if(id==null){
			id =1;
		}
		String path = null;
		try{
			PreparedStatement ps = con.prepareStatement("SELECT PATH FROM ENTRIES WHERE ID = ? AND DOWNLOAD_STATUS = 1");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				path = rs.getString("PATH");
			}
		}
		catch(SQLException e){
			System.out.println(e.toString());
		}

		return 	path;
	}

	public String getNameByid(Integer id) {
		if(id==null){
			id =1;
		}
		String path = null;
		try{
			PreparedStatement ps = con.prepareStatement("SELECT NAME FROM ENTRIES WHERE ID = ? AND DOWNLOAD_STATUS = 1");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				path = rs.getString("NAME");
			}
		}
		catch(SQLException e){
			System.out.println(e.toString());
		}

		return 	path;
		}	

}
