package com.mhv.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.mhv.bean.Pallier;
import com.mhv.dao.DAOException;
import com.mhv.dao.DAOFactory;
import com.mhv.dao.PallierDAO;

public class SetPallierPicture extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONF_DAO_FACTORY = "daofactory";
	public static final String REPOSITORY = "uploadPath";
	public static final String ATT_PALLIER = "pallier";
	public static final String ATT_FORM = "form";
	public static final String ATT_UPLOAD_ERROR = "upload_error";
	public static final String VIEW = "/WEB-INF/jsp/set_pallier_picture.jsp";
	public static final String NEXT_STEP_VIEW = "/WEB-INF/jsp/define_pallier_areas.jsp";
	
	private PallierDAO pallierDAO;
	
	public void init() throws ServletException {
		this.pallierDAO = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPallierDAO();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = "";
		try {
			uploadFile(request);
			nextPage = NEXT_STEP_VIEW;
		}
		catch(FileUploadException e) {
			request.setAttribute(ATT_UPLOAD_ERROR, "[FileUploadException] "+e.getMessage());
			nextPage = VIEW;
		}
		catch(DAOException e) {
			request.setAttribute(ATT_UPLOAD_ERROR, "[DAOException] "+e.getMessage());
			e.printStackTrace();
			nextPage = VIEW;
		}
		catch(Exception e) {
			request.setAttribute(ATT_UPLOAD_ERROR, "[Exception] "+e.getMessage());
			nextPage = VIEW;
		}
		finally {
			this.getServletContext().getRequestDispatcher(nextPage+"?pid="+request.getAttribute(ATT_PALLIER)).forward(request, response);
		}
	}

	private void uploadFile(HttpServletRequest request) throws FileUploadException, Exception {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		String repository = this.getServletConfig().getInitParameter(REPOSITORY);
		String pallierName = "";
		String fileToRename = "";
		if (isMultipart) {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List items = upload.parseRequest(request);
			Iterator iterator = items.iterator();
			while (iterator.hasNext()) {
				FileItem item = (FileItem) iterator.next();
				if (!item.isFormField()) {
					String fileName = item.getName();
					File path = new File(repository);
					if (!path.exists()) {
						if(!path.mkdirs())
							throw new Exception("Impossible de créer le répertoire "+path.getAbsolutePath());
					}
					File uploadedFile = new File(path+"/"+fileName);
					fileToRename = uploadedFile.getAbsolutePath();
					System.out.println("upload file at : "+uploadedFile.getAbsolutePath());
					item.write(uploadedFile);
				}
				else {
	                String fieldName = item.getFieldName();
	                String fieldValue = item.getString();
	                if(fieldName.equals("pallierName")) {
	                	pallierName = fieldValue;
	                }
				}
			}
			File tmp = new File(fileToRename);
			if(tmp.renameTo(new File(repository+"/"+pallierName+".png"))) {
				System.out.println("Rename file succeed : "+pallierName+".png");
				/* Finalement attribuer l'image au pallier ! */
				Pallier pallier = pallierDAO.getPallierByName(pallierName);
				pallier.setImagePath(repository+"/"+pallierName+".png");
				pallierDAO.modify(pallier);
				request.setAttribute(ATT_PALLIER, pallier);
			}
			else {
				System.out.println("Rename file ERROR !!");
				throw new Exception("Impossible de renommer l'image "+tmp.getAbsolutePath()+" en "+repository+"/"+pallierName+".png");
			}
		}
	}
}
