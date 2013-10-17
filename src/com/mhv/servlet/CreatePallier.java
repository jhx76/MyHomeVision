package com.mhv.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mhv.bean.Pallier;
import com.mhv.dao.DAOFactory;
import com.mhv.dao.PallierDAO;
import com.mhv.forms.CreatePallierForm;


public class CreatePallier extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_ERROR_UPLOAD_FILE = "error_upload_file";
	public static final String ATT_PALLIER_PICTURE_PATH = "pallierPicturePath";
	public static final String ATT_PALLIER = "pallier";
	public static final String ATT_FORM = "form";
	public static final String NEXT_STEP_VIEW = "/WEB-INF/jsp/set_pallier_picture.jsp";
	public static final String VIEW = "/WEB-INF/jsp/create_pallier.jsp";

	private PallierDAO pallierDAO;

	public void init() throws ServletException {
		this.pallierDAO = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPallierDAO();
	}

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CreatePallierForm form = new CreatePallierForm(pallierDAO);
		Pallier pallier = form.submitCreatePallier(request);
		request.setAttribute(ATT_PALLIER, pallier);
		request.setAttribute(ATT_FORM, form);
		if(!form.getErrorMap().isEmpty()) getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		else getServletContext().getRequestDispatcher(NEXT_STEP_VIEW).forward(request, response);
	}
}
