package com.mhv.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mhv.dao.DAOException;
import com.mhv.dao.DAOFactory;
import com.mhv.dao.UserDAO;


public class DeleteUser extends HttpServlet {
	private static final String VIEW = "/user_management";
	private static final String ATT_USER_ID = "id";
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String ERR_NUMBER_FORMAT = "err_number_format";
	private static final String ERR_DAO = "err_dao";
	private static final String ERROR_MAP = "errorMap";
	
	private UserDAO userDAO;
	
	private Map<String, String> errorMap = new HashMap<String, String>();
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
        this.userDAO = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUserDAO();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String strId = request.getParameter(ATT_USER_ID);
			Long id = Long.parseLong(strId);
			userDAO.deleteUserById(id);
		}
		catch(NumberFormatException e) {
			setError(ERR_NUMBER_FORMAT, e.getMessage());
		}
		catch(DAOException e) {
			setError(ERR_DAO, e.getMessage());
		}
		request.setAttribute(ERROR_MAP, errorMap);
		response.sendRedirect( request.getContextPath()+VIEW);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	private void setError(String field, String message) {
		errorMap.put(field, message);
	}
	
	public Map<String, String> getErrorMap() { return this.errorMap; }
	
}
