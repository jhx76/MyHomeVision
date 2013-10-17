package com.mhv.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Disconnection extends HttpServlet {
       
	//public static final String URL_REDIRECTION = "http://www.siteduzero.com";
	 
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        //response.sendRedirect( URL_REDIRECTION );
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/connection.jsp"/*"/WEB-INF/jsp/Home.jsp"*/).forward(request, response);
    }

}
