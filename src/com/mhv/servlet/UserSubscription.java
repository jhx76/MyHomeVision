package com.mhv.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mhv.bean.User;
import com.mhv.dao.DAOFactory;
import com.mhv.dao.UserDAO;
import com.mhv.forms.UserSubscriptionForm;

public class UserSubscription extends HttpServlet {
	public static final String VIEW = "/WEB-INF/jsp/user_subscription.jsp";
	public static final String ATT_FORM = "form";
	public static final String ATT_USER = "user";
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	private UserDAO userDAO;
	
	
	public void init() throws ServletException {
        this.userDAO = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUserDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        UserSubscriptionForm form = new UserSubscriptionForm(userDAO);
        User user = form.userSubscribe(request);
        request.setAttribute(ATT_FORM, form);
        request.setAttribute(ATT_USER, user);
        this.getServletContext().getRequestDispatcher(VIEW).forward( request, response );
	}
}