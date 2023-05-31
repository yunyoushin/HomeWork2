package 과제2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomsDAO {
	final String JDBC_DRIVER = "org.h2.Driver";
	final String JDBC_URL = "jdbc:h2:tcp://localhost/~/jwbookdb";
	
	// DB 연결을 가져오는 메서드, DBCP를 사용하는 것이 좋음
	public Connection open() {
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL,"jwbook","1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public List<Rooms> getAll() throws Exception {
		Connection conn = open();
		List<Rooms> roomsList = new ArrayList<>();
		
		String sql = "select aid, name, email, title, password, content, FORMATDATETIME(date,'yyyy-MM-dd') as cdate from rooms"; 
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		try(conn; pstmt; rs) {
			while(rs.next()) {
				Rooms r = new Rooms();
				r.setAid(rs.getInt("aid"));
				r.setName(rs.getString("name"));
				r.setEmail(rs.getString("email"));
				r.setTitle(rs.getString("title"));
				r.setPassword(rs.getString("password"));
				r.setContent(rs.getString("content"));
				r.setDate(rs.getString("cdate")); 
				
				roomsList.add(r);
			}
			return roomsList;			
		}
	}
	
	public Rooms getRooms(int aid) throws SQLException {
		Connection conn = open();
		
		Rooms r = new Rooms();
		String sql = "select aid, name, email, title, password, content, FORMATDATETIME(date,'yyyy-MM-dd') as cdate, content from rooms where aid=?";
	
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, aid);
		ResultSet rs = pstmt.executeQuery();
		
		rs.next();
		
		try(conn; pstmt; rs) {
			r.setAid(rs.getInt("aid"));
			r.setName(rs.getString("name"));
			r.setEmail(rs.getString("email"));
			r.setTitle(rs.getString("title"));
			r.setPassword(rs.getString("password"));
			r.setContent(rs.getString("content"));
			r.setDate(rs.getString("cdate"));
			pstmt.executeQuery();
			
		}return r;
	}
	
	public void addRooms(Rooms r) throws Exception {
		
		Connection conn = open();
		
		String sql = "insert into rooms(name,email,title,password,content,date) values(?,?,?,?,?,CURRENT_DATE())";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		try(conn; pstmt) {
			pstmt.setString(1, r.getName());
			pstmt.setString(2, r.getEmail());
			pstmt.setString(3, r.getTitle());
			pstmt.setString(4, r.getPassword());
			pstmt.setString(5, r.getContent());
			pstmt.executeUpdate();
		}
	}

public void modifyRooms(int aid,Rooms r) throws Exception {
		
		Connection conn = open();
		
		String sql =  "update rooms set name = ?, email = ?, title = ?, password=? , content=? where aid=?";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		try(conn; pstmt) {
			pstmt.setString(1, r.getName());
			pstmt.setString(2, r.getEmail());
			pstmt.setString(3, r.getTitle());
			pstmt.setString(4, r.getPassword());
			pstmt.setString(5, r.getContent());
			pstmt.setInt(6,r.getAid());
			pstmt.executeUpdate();
		}
	}

}

