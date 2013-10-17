package com.mhv.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mhv.dao.DAOException;
import com.mhv.dao.DAOFactory;
import com.mhv.dao.PallierDAO;


public class DeletePallier extends HttpServlet {

	private static final String VIEW = "/configure_habitation";
	private static final String ATT_PALLIER_ID = "id";
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String ATT_ERROR = "error";
	
	private PallierDAO pallierDAO;
	
	public void init() throws ServletException {
        this.pallierDAO = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPallierDAO();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String error = "";
		try {
			String strId = request.getParameter(ATT_PALLIER_ID);
			Long id = Long.parseLong(strId);
			pallierDAO.deleteById(id);
		}
		catch(DAOException e) {
			error = "[DAOException] "+e.getMessage();
		}
		request.setAttribute(ATT_ERROR, error);
		response.sendRedirect(request.getContextPath()+VIEW);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
}
