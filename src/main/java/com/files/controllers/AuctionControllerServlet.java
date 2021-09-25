package com.files.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.files.models.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.files.models.Auction;
import com.files.models.Bidding;
/**
 * Servlet implementation class AuctionControllerServlet
 */
@WebServlet("/AuctionControllerServlet")
public class AuctionControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	@Resource(name="jdbc/obidding") private DataSource dataSource;
//	private LoginUtil loginUtil;
//	@Override
//	public void init() throws ServletException {
//		// TODO Auto-generated method stub
//		super.init();
//		loginUtil=new LoginUtil(dataSource);
//	}
	private PrintWriter out;
    public AuctionControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	out=response.getWriter();
    	clearAttributes(request,response);
		String page=request.getParameter("page");
		switch(page) {
			case "create_auction":createAuctionView(request,response);break; 
			case "auction_history":auctionHistoryView(request,response);break; 
			case "update_auction":
			try {
				updateAuctionView(request,response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}break; 
			
			default:showDashBoard(request,response);break;
		}
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

	private void updateAuctionView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		RequestDispatcher dispatcher;
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet res=null;
		try {
		int auction_id=Integer.parseInt(request.getParameter("id"));
		String sql="select * from auctions where id=?";
		 conn=dataSource.getConnection();
		 stmt=conn.prepareStatement(sql);
		stmt.setLong(1,auction_id);
		 res=stmt.executeQuery();
		if(res.next()) {
			Auction auction=new Auction(res.getInt("id"),res.getInt("min_value"),res.getString("auction_name"),res.getString("item_name"),res.getString("start_timestamp"),res.getString("end_timestamp"),res.getBoolean("is_active"));
			request.setAttribute("auction",auction);
//			out.println(user.toString());
		}
		dispatcher=request.getRequestDispatcher("update_auction.jsp");
		dispatcher.forward(request,response);
		}
		catch(Exception e) {
			out.println(e);
			e.printStackTrace();
		}
		close(conn,stmt,res);
	}

	private void clearAttributes(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("success-msg",null);
		request.getSession().setAttribute("warning",null);
	}

	private void showDashBoard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		dispatcher=request.getRequestDispatcher("dashboard.jsp");
		dispatcher.forward(request,response);
	}

	private void createAuctionView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		if(request.getSession().getAttribute("user")==null)
		{
			dispatcher=request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request,response);
			return;
		}
		dispatcher=request.getRequestDispatcher("create_auction.jsp");
		request.getSession().setAttribute("success-msg","Auction Created");
		dispatcher.forward(request,response);
		
	}

	private void auctionHistoryView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("user")==null)
		{
			RequestDispatcher dispatcher=request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request,response);
			return;
		}
		User user=(User)request.getSession().getAttribute("user");
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet res=null;
		try {
			 conn=dataSource.getConnection();
			String sql="select * from auctions where user_id=?";
			 stmt=conn.prepareStatement(sql);
			stmt.setLong(1,user.getId());
			 res=stmt.executeQuery();
			ArrayList<Auction> active_auctions=new ArrayList<>();
			ArrayList<Auction> inactive_auctions=new ArrayList<>();
			
			while(res.next()) {
				boolean active=res.getBoolean("is_active");
				Auction a=new Auction(res.getInt("id"),res.getInt("min_value"),res.getString("auction_name"),res.getString("item_name"),res.getString("start_timestamp"),res.getString("end_timestamp"),res.getBoolean("is_active"));
				if(active) active_auctions.add(a);
				else inactive_auctions.add(a);
			}
			request.setAttribute("active_auctions",active_auctions);
			request.setAttribute("inactive_auctions",inactive_auctions);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println(e);
		}
		
		close(conn,stmt,res);
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("auction_history.jsp");
		request.getSession().setAttribute("success-msg","Auction Created");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("user")==null) {showLogin(request,response);return;}
		out=response.getWriter();
		clearAttributes(request,response);
		String command=request.getParameter("command");
		switch(command) {
			case "create_auction":createAuction(request,response);break;
			case "update_auction":updateAuctionData(request,response);break;
			case "delete_auction":deleteAuction(request,response);break;
		}
	}

	private void deleteAuction(HttpServletRequest request, HttpServletResponse response) {
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet res=null;
		try {
			int auction_id=Integer.parseInt(request.getParameter("auction_id"));

			 conn = dataSource.getConnection();
			String sql="delete from auctions where id=?";
			
			 stmt=conn.prepareStatement(sql);
			stmt.setLong(1,auction_id);
			stmt.executeUpdate();
			
			
			 sql="delete from biddings where auction_id=?";
			 stmt=conn.prepareStatement(sql);
			 stmt.setLong(1,auction_id);

			stmt.executeUpdate();
			request.setAttribute("success-msg","Auction & Biddings Removed");
			auctionHistoryView(request,response);
		}
		catch(Exception e) {
			out.println(e);
			e.printStackTrace();
		}
		close(conn,stmt,res);
	}

	private void updateAuctionData(HttpServletRequest request, HttpServletResponse response) {
		try {
			int auction_id=Integer.parseInt(request.getParameter("auction_id"));
			String auction_name=request.getParameter("auction_name");
			String item_name=request.getParameter("item_name");
			String start_time=request.getParameter("start_time");
			String end_time=request.getParameter("end_time");
			Integer amount=Integer.parseInt(request.getParameter("min_bid"));
			boolean isActive=request.getParameter("is_active").equals("1");
			
			
			Connection conn = dataSource.getConnection();
			String sql="update auctions set auction_name=?,item_name=?,start_timestamp=?,end_timestamp=?,min_value=?,is_active=? where id=?";
			
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setString(1,auction_name);
			stmt.setString(2,item_name);
			stmt.setString(3,start_time);
			stmt.setString(4,end_time);
			stmt.setLong(5,amount);
			stmt.setLong(7,auction_id);
			stmt.setBoolean(6,isActive);
			
			stmt.executeUpdate();
			
			request.setAttribute("success-msg","Successfully Updated");
			auctionHistoryView(request,response);
		}
		catch(Exception e) {
			out.println(e);
			e.printStackTrace();
		}
		
	}

	private void showLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		dispatcher=request.getRequestDispatcher("login.jsp");
		dispatcher.forward(request,response);
	}

	private void createAuction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		User user=(User)request.getSession().getAttribute("user");
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet res=null;
		try {
			 conn=dataSource.getConnection();
			String auction_name=request.getParameter("auction_name");
			String item_name=request.getParameter("item_name");
			String start_time=request.getParameter("start_time");
			String end_time=request.getParameter("end_time");
			Integer amount=Integer.parseInt(request.getParameter("min_bid"));
//			out.println(auction_name+" "+item_name+" "+start_time+" "+end_time+" "+amount);
			conn = dataSource.getConnection();
			String sql="insert into auctions(auction_name,item_name,start_timestamp,end_timestamp,min_value,image,user_id) values(?,?,?,?,?,null,?);";
			
			 stmt=conn.prepareStatement(sql);
			stmt.setString(1,auction_name);
			stmt.setString(2,item_name);
			stmt.setString(3,start_time);
			stmt.setString(4,end_time);
			stmt.setLong(5,amount);
			stmt.setLong(6,user.getId());
			try {
				
				stmt.execute();
				request.setAttribute("success-msg","Auction Created");
				RequestDispatcher dispatcher=request.getRequestDispatcher("dashboard.jsp");
				dispatcher.forward(request,response);
			}
			catch(Exception e) {
				e.printStackTrace();
				out.println(e);
				out.println("sql error!");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close(conn,stmt,res);
	}

}
