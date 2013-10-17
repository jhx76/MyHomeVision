package com.mhv.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xpl.XHCPException;
import com.xpl.xPL_Device;
import com.xpl.xPL_HalServer_Proxy;

/**
 * Servlet implementation class HalDeviceList
 */
public class HalDeviceList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONF_HALSERVER_PROXY = "halserverproxy";
	private static final String ATT_DEVICE_LIST = "deviceList";
	private static final String ATT_ERROR_MAP = "errorMap";
	private xPL_HalServer_Proxy serverProxy;
	public static final String VIEW = "/WEB-INF/jsp/hal_device_list.jsp";
	private Map<String, String> errorMap  = new HashMap<String, String>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HalDeviceList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public void init() throws ServletException {
		serverProxy = (xPL_HalServer_Proxy) getServletContext().getAttribute(CONF_HALSERVER_PROXY);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			serverProxy.refreshDeviceList();
			List<xPL_Device> deviceList = serverProxy.getDeviceList();
			request.setAttribute(ATT_DEVICE_LIST, deviceList);
		}
		catch(XHCPException e) {
			errorMap.put("SERVER_ERROR", e.getMessage()+" ("+e.getCause().getMessage()+")");
			request.setAttribute(ATT_ERROR_MAP, errorMap);
		}
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

}
