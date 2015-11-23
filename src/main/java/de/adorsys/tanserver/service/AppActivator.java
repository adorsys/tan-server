/**
 * 
 */
package de.adorsys.tanserver.service;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.Unirest;

/**
 * @author sso
 */
@WebListener
public class AppActivator implements ServletContextListener {
	private static final Logger LOG = LoggerFactory.getLogger(AppActivator.class);

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			Unirest.shutdown();
		} catch (IOException e) {
			LOG.error("unirest shutdown", e);
		}

	}

}
