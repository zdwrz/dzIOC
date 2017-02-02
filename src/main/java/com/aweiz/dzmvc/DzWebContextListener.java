package com.aweiz.dzmvc;

import com.aweiz.dzioc.BeanFactory;
import com.aweiz.dzioc.annotations.DzConfigure;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by daweizhuang on 2/2/17.
 */
public class DzWebContextListener implements ServletContextListener {
    private static Logger LOGGER = Logger.getLogger(DzWebContextListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Hit Context initialized method");
        String configClassName = sce.getServletContext().getInitParameter("ConfigurationClassName");
        try {
            Class configClass = Class.forName(configClassName);
            DzWebContext webContext = new DzWebContext();
            webContext.initializeWebContext(new BeanFactory(configClass));
            sce.getServletContext().setAttribute("WebContext",webContext);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Hit Context destroyed method");
    }
}
