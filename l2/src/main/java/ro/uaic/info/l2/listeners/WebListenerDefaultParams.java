package ro.uaic.info.l2.listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class WebListenerDefaultParams implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();

        // Read default properties from context init parameters (web.xml)
        String defaultOrder = event.getServletContext().getInitParameter("defaultOrder");
        String defaultSize = event.getServletContext().getInitParameter("defaultSize");

        // Store the default properties in application scope attributes
        context.setAttribute("defaultOrder", defaultOrder);
        context.setAttribute("defaultSize", defaultSize);
    }
}