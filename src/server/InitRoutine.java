package server;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import backend.ReadDataServer;

public class InitRoutine implements ServletContextListener {

	ServletContext context;

	public void contextInitialized(ServletContextEvent contextEvent) {
		// Put it into the context
		context = contextEvent.getServletContext();
		context.setAttribute("data", new ReadDataServer());

	}

	public void contextDestroyed(ServletContextEvent contextEvent) {
		context = contextEvent.getServletContext();
		context.setAttribute("data", null);
	}

}
