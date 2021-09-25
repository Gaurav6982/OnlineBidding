package com.files.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.files.models.User;

/**
 * Servlet implementation class HomeControllerServlet
 */
@WebServlet("/HomeControllerServlet")
public class HomeControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String page=request.getParameter("page");
		
		switch(page) {
			case "dashboard":showDashboard(request,response);break;
			default:showDashboard(request,response);break;
		}
		
	}

	private void showDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user=(User)request.getSession().getAttribute("user");
		RequestDispatcher dispatcher;
		if(user==null) 
			dispatcher=request.getRequestDispatcher("login.jsp");
		else
			dispatcher=request.getRequestDispatcher("dashboard.jsp");
		dispatcher.forward(request,response);
	}

}
