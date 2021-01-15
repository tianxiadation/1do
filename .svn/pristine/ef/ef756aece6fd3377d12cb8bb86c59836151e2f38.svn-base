package com.luqi.util;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

public class Log4jlnitServlet extends HttpServlet {
	private static final long serialVersionUID = 7525527957967196513L;

	public void init(ServletConfig config) throws ServletException {


		String root = config.getServletContext().getRealPath("/");

		String log4jLocation = config.getInitParameter("log4jLocation");

		System.setProperty("WebRoot", root);

		if (log4jLocation!=null) {

		PropertyConfigurator.configure(root + log4jLocation);

		}

		 

		    }
}
