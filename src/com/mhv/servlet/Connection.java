package com.mhv.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mhv.bean.User;
import com.mhv.forms.ConnectionForm;


public class Connection extends HttpServlet {
	private static final String ATT_USER = "user";
	private static final String ATT_FORM = "form";
	private static final String ATT_USER_SESSION = "userSession";
	private static final String VIEW = "/WEB-INF/jsp/connection.jsp";
	private static final String HOME = "/WEB-INF/jsp/Home.jsp";
	
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher(VIEW).forward( request, response );
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectionForm form = new ConnectionForm();
		User user = form.userConnection(request);
		HttpSession session = request.getSession();
		if(form.getErrorMap().isEmpty())
            session.setAttribute(ATT_USER_SESSION, user);
        else 
            session.setAttribute(ATT_USER_SESSION, null);
		request.setAttribute(ATT_FORM, form);
        request.setAttribute(ATT_USER, user);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request,response);
	}
}
