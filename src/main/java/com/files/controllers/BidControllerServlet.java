package com.files.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.files.models.Auction;
import com.files.models.Bidding;
import com.files.models.User;

/**
 * Servlet implementation class BidControllerServlet
 */
@WebServlet("/BidControllerServlet")
public class BidControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BidControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Resource(name="jdbc/obidding") private DataSource dataSource;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    PrintWriter out;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		out=response.getWriter();
		
		
		String command=request.getParameter("command");
		switch(command) {
			case "show_bid":showBidPage(request,response); break;
			case "available_auctions":showAvailableAuctions(request,response);break;
			default: showIndex(request,response);break;
		}
		
	}
	
	private void showIndex(HttpServletRequest request, HttpServletResponse response) {
		try {
			RequestDispatcher dispatcher=request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request,response);
		}
		catch(Exception e) {
			out.println(e);
			e.printStackTrace();
		}
		
	}

	private void showBidPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher=null;
		if(request.getSession().getAttribute("user")==null) {
			dispatcher=request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request,response);
			return;
		}
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet res=null;
		try {
			String id=request.getParameter("auction");
			if(id.equals("")) {showAuctionsPage(request,response,"Auction not defined!"); return;}
			conn=dataSource.getConnection();
			String sql="select * from auctions where id=?";
			stmt=conn.prepareStatement(sql);
			stmt.setLong(1,Integer.parseInt(id));
			res=stmt.executeQuery();
			if(res.next()) {
				Auction a=new Auction(res.getInt("id"),res.getInt("min_value"),res.getString("auction_name"),res.getString("item_name"),res.getString("start_timestamp"),res.getString("end_timestamp"),true);
				request.setAttribute("auction",a);
				request.setAttribute("item_name",res.getString("item_name"));
				Map<Integer,String> user_details=new HashMap<>();
				ArrayList<Bidding> biddings=new ArrayList<>();
				setBiddings(a.getId(),user_details,biddings);
				request.setAttribute("biddings",biddings);
				request.setAttribute("users",user_details);
				
				//top bidding details
				//sql="select name,amount from users inner join biddings on users.id=biddings.user_id where user_id=(select user_id from biddings where auction_id=? and amount=(select max(amount) from biddings where auction_id=?)); ";
				
				if(biddings.size()>0) {
					request.setAttribute("top_bidder_name",user_details.get(biddings.get(0).getUser_id()));
					request.setAttribute("top_bidding_amount",biddings.get(0).getAmount());
				}
				
				//get last bidding value of user;
				int last_bid_value=0;
				User user=(User)request.getSession().getAttribute("user");
				sql="select amount from biddings where auction_id=? and user_id=? order by amount desc limit 1;";
				stmt=conn.prepareStatement(sql);
				stmt.setLong(1,a.getId());
				stmt.setLong(2,user.getId());
				res=stmt.executeQuery();
				if(res.next()) last_bid_value=res.getInt("amount");
				request.setAttribute("last_bid_value",last_bid_value);
				
			}
			else {
				showAuctionsPage(request,response,"No Auction Found!"); return;
			}
			dispatcher=request.getRequestDispatcher("particular-bid.jsp");
			dispatcher.forward(request,response);
		}
		catch(Exception e) {
			out.println(e);
			e.printStackTrace();
		}
		close(conn,stmt,res);
	}

	

	

	private void setBiddings(int id, Map<Integer, String> user_details, ArrayList<Bidding> biddings) {
		try {
			Connection conn=dataSource.getConnection();
			
			String sql="select * from biddings where auction_id=? order by amount desc limit 10";
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setLong(1,id);
			ResultSet bids=stmt.executeQuery();
			while(bids.next()) {
				biddings.add(new Bidding(bids.getInt("id"),bids.getInt("auction_id"),bids.getInt("user_id"),bids.getInt("amount")));
				sql="select * from users where id=?";
				stmt=conn.prepareStatement(sql);
				stmt.setLong(1,bids.getInt("user_id"));
				ResultSet r=stmt.executeQuery();
				if(r.next()) {
					user_details.put(bids.getInt("user_id"),r.getString("name"));
				}
			} 
			close(conn,stmt,bids);
		}
		catch(Exception e) {
			out.println(e);
			e.printStackTrace();
		}
	}

	private void showAuctionsPage(HttpServletRequest request, HttpServletResponse response, String string) {
		try {
			request.setAttribute("warning",string);
			showAvailableAuctions(request,response);
			return;
		}
		catch(Exception e) {
			out.println(e);
			e.printStackTrace();
		}
		
	}
	private void showAvailableAuctions(HttpServletRequest request, HttpServletResponse response) {
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet res=null;
		try {
			SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
//			out.println(ft.format(date));
			conn=dataSource.getConnection();
			String sql="update auctions set is_active=0 where end_timestamp<?";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1,ft.format(date));
			stmt.executeUpdate();
			//d1.compareTO(d2)>0 d1 occurs after d2
			 conn=dataSource.getConnection();
			sql="select * from auctions where is_active=1;";
			stmt=conn.prepareStatement(sql);
//			stmt.setString(1,ft.format(date));
			res=stmt.executeQuery();
					
			ArrayList<Auction> available_auctions=new ArrayList<>();
			ArrayList<Auction> upcoming_auctions=new ArrayList<>();
			Map<Integer,ArrayList<Bidding>> bidding_map=new HashMap<>();
			Map<Integer,String> user_details=new HashMap<>();
			ArrayList<Bidding> biddings=null;
			while(res.next()) {
				Auction a=new Auction(res.getInt("id"),res.getInt("min_value"),res.getString("auction_name"),res.getString("item_name"),res.getString("start_timestamp"),res.getString("end_timestamp"),true);
				if(ft.parse(a.getStart_timestamp()).compareTo(date)>0)
					upcoming_auctions.add(a);
				else
					available_auctions.add(a);
				biddings=new ArrayList<>();
				setBiddings(a.getId(),user_details,biddings);
				bidding_map.put(a.getId(),biddings);
			}
			request.setAttribute("available_auctions",available_auctions);
			request.setAttribute("upcoming_auctions",upcoming_auctions);
			request.setAttribute("biddings_mapping",bidding_map);
			request.setAttribute("users",user_details);
			
			RequestDispatcher dispatcher=request.getRequestDispatcher("biddings.jsp");
			dispatcher.forward(request,response);
		}
		catch(Exception e) {
			out.println(e);
			e.printStackTrace();
		}
		close(conn,stmt,res);
		
	}
	private void close(Connection conn, PreparedStatement stmt, ResultSet res) {
		// TODO Auto-generated method stub
		try {
			if(conn!=null) conn.close();
			if(stmt!=null) stmt.close();
			if(res!=null) res.close();
		}
		catch(Exception e) {
			out.println(e);
			e.printStackTrace();
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
