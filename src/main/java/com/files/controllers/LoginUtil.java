package com.files.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.files.models.User;
import com.files.utils.Pair;

import javax.sql.DataSource;

public class LoginUtil {
	private DataSource dataSource;
	
	public LoginUtil(DataSource dataSource) {
		this.dataSource=dataSource;
	}
	
	public Pair attemptLogin(String email,String pass) {
		Connection conn=null;
		PreparedStatement Stmt=null;
		ResultSet myRes=null;
		
		try {
			conn=dataSource.getConnection();
			String sql="select * from users where email=? and password=?;";
			Stmt=conn.prepareStatement(sql);
			Stmt.setString(1,email);
			Stmt.setString(2,pass);
			myRes=Stmt.executeQuery();
			
			if(myRes.next()) {
					return new Pair(new User(myRes.getInt("id"),myRes.getString("name"),myRes.getString("email")),"success");
			}
			else {
				return new Pair(null,"mismatch");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Pair(null,"failed");
		
	}
	public Pair registerUser(User user) {
		Connection conn=null;
		PreparedStatement Stmt=null;
//		ResultSet myRes=null;
		
		try {
			conn=dataSource.getConnection();
			String sql="insert into users(name,email,password) values(?,?,?);";
			Stmt=conn.prepareStatement(sql);
			Stmt.setString(1,user.getName());
			Stmt.setString(2,user.getEmail());
			Stmt.setString(3,user.getPassword());
			Stmt.execute();
//			if()
				return new Pair(user,"success");
//			return new Pair(null,"query-fail "+Stmt+user.getName()+" "+user.getEmail()+" "+user.getPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Pair(null,"failed");
		
	}
	
}
