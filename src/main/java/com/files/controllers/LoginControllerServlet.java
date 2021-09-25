package com.files.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.files.models.User;
import com.files.utils.Pair;

/**
 * Servlet implementation class LoginControllerServlet
 */
@WebServlet("/LoginControllerServlet")
public class LoginControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Resource(name="jdbc/obidding") private DataSource dataSource;
    private LoginUtil loginUtil;
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		loginUtil=new LoginUtil(dataSource);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		
		String command=request.getParameter("command");
		request.getSession().setAttribute("warning",null);
		try {
		switch(command) {
			case "login": login(request,response);break;
			case "register":register(request,response); break;
			case "logout":logout(request,response); break;
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getSession().invalidate();
		RequestDispatcher dispatcher=request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request,response);
	}

	private void register(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		RequestDispatcher dispatcher;
		if(request.getParameter("user")!=null) {
			dispatcher=request.getRequestDispatcher("dashboard.jsp");
			dispatcher.forward(request,response);
		}
		Connection conn=dataSource.getConnection();
		PreparedStatement stmt=conn.prepareStatement("select * from users where email=?;");
		ResultSet myRes=null;
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String confirm=request.getParameter("confirm");
		User user=new User(name,email,password);
		PrintWriter out=response.getWriter();
		stmt.setString(1,email);
		myRes=stmt.executeQuery();
		if(!myRes.next()) {
			if(!password.equals(confirm))
				request.getSession().setAttribute("warning","Password Not Matching");
			else {
				Pair p=loginUtil.registerUser(user);
				out.println(p.getMsg()+" "+p.getData());
				if(p.getMsg().equals("success")) {
					request.getSession().setAttribute("user",(User)p.getData());
					dispatcher=request.getRequestDispatcher("dashboard.jsp");
					dispatcher.forward(request,response);
				}
				else if(p.getMsg().equals("mismatch")) {
					request.getSession().setAttribute("warning","Email Password Mismatch");
					
				}
				else {
					request.getSession().setAttribute("warning","Something Went Wrong!!");
				}
			}
		}
		else {
			request.getSession().setAttribute("warning","Email Already Exist!!");
		}
		dispatcher=request.getRequestDispatcher("register.jsp");
		dispatcher.forward(request,response);
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		RequestDispatcher dispatcher;
		if(request.getParameter("user")!=null) {
			dispatcher=request.getRequestDispatcher("dashboard.jsp");
			dispatcher.forward(request,response);
		}
		Connection conn=dataSource.getConnection();
		PreparedStatement stmt=conn.prepareStatement("select * from users where email=?;");
		ResultSet myRes=null;
		String email="";
		String password="";
		PrintWriter out=response.getWriter();
		try {
			conn=dataSource.getConnection();
			stmt=conn.prepareStatement("select * from users where email=?;");
			email=request.getParameter("email");
			password=request.getParameter("password");
			stmt.setString(1,email);
			myRes=stmt.executeQuery();
		}
		catch(Exception e) {
			e.printStackTrace();
			out.println("Database Connection Error!");
		}
		
		if(myRes.next()) {
			Pair p=loginUtil.attemptLogin(email,password);
			if(p.getMsg().equals("success")) {
				request.getSession().setAttribute("user",(User)p.getData());
				dispatcher=request.getRequestDispatcher("dashboard.jsp");
				dispatcher.forward(request,response);
				return;
			}
			else if(p.getMsg().equals("mismatch")) {
				request.getSession().setAttribute("warning","Email Password Mismatch");
				
			}
			else {
				request.getSession().setAttribute("warning","Something Went Wrong!!");
			}
			
		}
		else {
			request.getSession().setAttribute("warning","Email Not Found!!");
		}
		dispatcher=request.getRequestDispatcher("login.jsp");
		dispatcher.forward(request,response);
		
	}

}
