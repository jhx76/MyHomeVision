package com.mhv.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mhv.bean.Pallier;
import com.mhv.dao.DAOFactory;
import com.mhv.dao.PallierDAO;


public class ConfigureHabitation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String VIEW = "/WEB-INF/jsp/configure_habitation.jsp";   
    private static final String ATT_PALLIER_LIST = "pallierList";
	
    private PallierDAO pallierDAO;
    
    public void init() throws ServletException {
        this.pallierDAO = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPallierDAO();
	}
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Pallier> pallierList = pallierDAO.getPallierList();
		request.setAttribute(ATT_PALLIER_LIST, pallierList);
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
	}

}
