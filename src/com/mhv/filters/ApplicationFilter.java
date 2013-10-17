package com.mhv.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ApplicationFilter implements Filter {

	private static final String ATT_USER_SESSION = "userSession";
	private static final String ACCESS_REFUSED = "connection";
	public void init( FilterConfig config ) throws ServletException {
		
    }
 
    public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain ) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();

        String chemin = request.getRequestURI().substring(request.getContextPath().length());
        if (chemin.startsWith("/styles")) {
            chain.doFilter( request, response );
            return;
        }

        if (session.getAttribute(ATT_USER_SESSION) == null)
            request.getRequestDispatcher(ACCESS_REFUSED).forward(request,response);
        else
            chain.doFilter(request, response);
    }
 
    public void destroy() {
    	
    	
    }

}
