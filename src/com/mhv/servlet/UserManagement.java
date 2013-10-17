package com.mhv.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mhv.bean.User;
import com.mhv.dao.DAOFactory;
import com.mhv.dao.UserDAO;

public class UserManagement extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_USER_LIST = "userList";
	public static final String ATT_ERROR_MAP = "errorMap";
    public static final String VIEW = "/WEB-INF/jsp/users.jsp";
 
    private UserDAO userDAO;
    
    public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
        this.userDAO = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUserDAO();
	}
    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	List<User> userList = userDAO.getUserList();
    	request.setAttribute(ATT_USER_LIST, userList);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }
}
