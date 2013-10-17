package com.mhv.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mhv.dao.DAOFactory;

public class DAOFactoryConfiguration implements ServletContextListener  {

	
	private static final String ATT_DAO_FACTORY = "daofactory";
	private DAOFactory daoFactory;
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		/* Nothing to do... */
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		/* register the DAOFactory instance in the application scope */
        ServletContext servletContext = event.getServletContext();
        this.daoFactory = DAOFactory.getInstance();
        servletContext.setAttribute( ATT_DAO_FACTORY, this.daoFactory );
	}
}
