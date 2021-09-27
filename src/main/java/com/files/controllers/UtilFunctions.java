package com.files.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.files.models.Auction;
import com.files.models.Bidding;
import com.files.models.TopBidder;
import com.files.models.User;

public class UtilFunctions {
	private DataSource dataSource;
	
	public UtilFunctions(DataSource dataSource) {
		this.dataSource=dataSource;
	}
	
	public Auction getAuction(int id) {
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet res=null;
		Auction a=null;
		try {
			conn=dataSource.getConnection();
			String sql="select * from auctions where id=?";
			stmt=conn.prepareStatement(sql);
			stmt.setLong(1,id);
			res=stmt.executeQuery();
			if(res.next())
				a=new Auction(res.getInt("id"),res.getInt("min_value"),res.getString("auction_name"),res.getString("item_name"),res.getString("start_timestamp"),res.getString("end_timestamp"),res.getBoolean("is_active"));
			
		}
		catch(Exception e ) {
			e.printStackTrace();
		}
		close(conn,stmt,res);
		return a;
	}
	public Bidding getBidding(int id) {
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet res=null;
		Bidding a=null;
		try {
			conn=dataSource.getConnection();
			String sql="select * from biddings where id=?";
			stmt=conn.prepareStatement(sql);
			stmt.setLong(1,id);
			res=stmt.executeQuery();
			if(res.next())
				new Bidding(res.getInt("id"),res.getInt("auction_id"),res.getInt("user_id"),res.getInt("amount"));
		}
		catch(Exception e ) {
			e.printStackTrace();
		}
		close(conn,stmt,res);
		return a;
	}
	public User getUser(int id) {
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet res=null;
		User user=null;
		try {
			conn=dataSource.getConnection();
			String sql="select name,email from users where id=?";
			stmt=conn.prepareStatement(sql);
			stmt.setLong(1,id);
			res=stmt.executeQuery();
			if(res.next()) {
				user=new User();
				user.setId(id);
				user.setName(res.getString("name"));
				user.setEmail(res.getString("email"));
			}
		}
		catch(Exception e ) {
			e.printStackTrace();
		}
		close(conn,stmt,res);
		return user;
	}
	public TopBidder getTopBidder(int auction_id) {
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet res=null;
		TopBidder top_bidder=null;
		try {
			conn=dataSource.getConnection();
			String sql="select user_id,amount from biddings where auction_id=? order by amount desc limit 1";
			stmt=conn.prepareStatement(sql);
			stmt.setLong(1,auction_id);
			res=stmt.executeQuery();
			if(res.next()) {
			
				User user=getUser(res.getInt("user_id"));
				int amount=res.getInt("amount");
				 top_bidder=new TopBidder(user,amount);
			}
		}
		catch(Exception e ) {
			e.printStackTrace();
		}
		close(conn,stmt,res);
		return top_bidder;
	}
	private void close(Connection conn, PreparedStatement stmt, ResultSet res) {
		// TODO Auto-generated method stub
		try {
			if(conn!=null) conn.close();
			if(stmt!=null) stmt.close();
			if(res!=null) res.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void setAuctionsClosed() {
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet res=null;
		try {
			SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
//			System.out.println(ft.format(date));
			conn=dataSource.getConnection();
			String sql="update auctions set is_active=0 where end_timestamp<=?";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1,ft.format(date));
			stmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		close(conn,stmt,res);
	}
	
}
