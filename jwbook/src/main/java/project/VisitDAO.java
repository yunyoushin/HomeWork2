package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class VisitDAO {
	final String JDBC_DRIVER = "org.h2.Driver";
	final String JDBC_URL = "jdbc:h2:tcp://localhost/~/jwbookdb";
	
	
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
	
	public void addVisit(Visit n) throws Exception {
	    Connection conn = open();

	    String sql = "insert into visit(name, email, date, title, content, password) VALUES (?, ?, CURRENT_TIMESTAMP(), ?, ?, ?)";
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	 
	    try (conn; pstmt) {
	        pstmt.setString(1, n.getName());
	        pstmt.setString(2, n.getEmail());
	        pstmt.setString(3, n.getTitle());
	        pstmt.setString(4, n.getContent());
	        pstmt.setString(5, n.getPassword());
	        pstmt.executeUpdate();
	    }
	}
	
	public List<Visit> getAll() throws Exception {
		Connection conn = open();
		List<Visit> visitList = new ArrayList<>();
		
		String sql = "select aid, name, email, SUBSTRING(date from 1 for 10) as cdate, title from visit";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		try(conn; pstmt; rs) {
			while(rs.next()) {
				Visit n = new Visit();
				n.setAid(rs.getInt("aid"));
				n.setName(rs.getString("name"));
				n.setEmail(rs.getString("email")); 
				n.setDate(rs.getString("cdate"));
				n.setTitle(rs.getString("title"));
				visitList.add(n);
			}			
		}
		return visitList;
	}

	public Visit getVisit(int aid) throws SQLException {
		Connection conn = open();
		
		Visit n = new Visit();
		String sql = "select aid, name, email, title, content, password from visit where aid=?";
	
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, aid);
		ResultSet rs = pstmt.executeQuery();

		rs.next();
		
		try(conn; pstmt; rs) {
			n.setAid(rs.getInt("aid"));
			n.setName(rs.getString("name"));
			n.setEmail(rs.getString("email")); 
			n.setTitle(rs.getString("title"));
			n.setContent(rs.getString("content"));
			n.setPassword(rs.getString("password"));
			pstmt.executeQuery();
		}
		return n;
	}
	
	public void modVisit(Visit n, int aid) throws SQLException {
		Connection conn = open();
		
		String sql = "update visit set name = ?, email = ?, title = ?, content=?, password=? where aid=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		try (conn; pstmt) {
	        pstmt.setString(1, n.getName());
	        pstmt.setString(2, n.getEmail());
	        pstmt.setString(3, n.getTitle());
	        pstmt.setString(4, n.getContent());
	        pstmt.setString(5, n.getPassword());
	        pstmt.setInt(6, aid);
	        pstmt.executeUpdate();
	    }
	}
}
