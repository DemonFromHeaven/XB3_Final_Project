package rest;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import backend.ReadData;

public class InitRoutines implements ServletContextListener {

	ServletContext context;
	ReadData data;
	
	public void contextInitialized(ServletContextEvent contextEvent) {
		
		// Build the data in memory
		data = new ReadData();

		// Put it into the context
		context = contextEvent.getServletContext();
		context.setAttribute("data", data);
		
	}
	
	public void contextDestroyed(ServletContextEvent contextEvent) {
		context = contextEvent.getServletContext();
		
	}
	
}
