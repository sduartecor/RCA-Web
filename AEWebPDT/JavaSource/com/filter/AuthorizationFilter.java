package com.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AuthorizationFilter extends HttpFilter {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
         
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);    
        String pageRequested = req.getRequestURI().toString();    
      
        if(session==null){   
        	session = req.getSession(true); // will create a new session   
            resp.sendRedirect("/AEWeb3/login.xhtml");   
        }else if((session.getAttribute("gestionUsuario")== null) && (!pageRequested.contains("login.xhtml"))){      
            resp.sendRedirect("/AEWeb3/login.xhtml");   
        }else if((session.getAttribute("gestionUsuario")!= null) && (pageRequested.contains("login.xhtml"))) {
       	 resp.sendRedirect("/AEWeb3/index.xhtml");   
       }else{   
         
        	//System.out.println(session.getAttribute("gestionUsuario"));
        chain.doFilter(request, response); // continue filtering   
        } 
        
    }
    
}
