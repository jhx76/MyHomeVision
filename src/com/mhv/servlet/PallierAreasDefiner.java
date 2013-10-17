package com.mhv.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mhv.bean.Pallier;
import com.mhv.dao.DAOException;
import com.mhv.dao.DAOFactory;
import com.mhv.dao.PallierDAO;

public class PallierAreasDefiner extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/jsp/define_pallier_areas.jsp";
	private static final String CONF_DAO_FACTORY = "daofactory";
	
	private PallierDAO pallierDAO;
	
	public void init() throws ServletException {
		this.pallierDAO = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPallierDAO();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pallierId = (String) request.getParameter("pid");
		System.out.println("pallier id : "+pallierId);
		Pallier pallier = null;
		try {
			pallier = pallierDAO.getPallierById(Long.parseLong(pallierId));
			request.setAttribute("pallier", pallier);
		}
		catch(DAOException e) {
			request.setAttribute("error", "[DAOException] "+e.getMessage());
			e.printStackTrace();
		}
		catch(NumberFormatException e) {
			request.setAttribute("error", "[NumberFormatException] "+e.getMessage());
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		//TODO
	}
	
}
