package com.mhv.config;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mhv.dao.DAOConfigurationException;
import com.xpl.xPL_HalServer_Proxy;

public class HalServerProxyConfiguration implements ServletContextListener {

	private static final String ATT_HALSERVER_PROXY = "halserverproxy";
	private xPL_HalServer_Proxy proxy;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		if(proxy != null) {
			proxy.disconect();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {		
		ServletContext servletContext = event.getServletContext();
		proxy = xPL_HalServer_Proxy.getInstance();
		try {
			proxy.connect();
		}
		catch(UnknownHostException e) {
			throw new DAOConfigurationException("[UnknownHostException] Impossible de se connecter au server xPLHal", e);
		}
		catch(IOException e) {
			throw new DAOConfigurationException("[IOException] Impossible de se connecter au server xPLHal", e);
		}
		servletContext.setAttribute( ATT_HALSERVER_PROXY, proxy);
	}
}
