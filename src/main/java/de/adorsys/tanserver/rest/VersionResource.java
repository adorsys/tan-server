package de.adorsys.tanserver.rest;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Path("version")
public class VersionResource {
	private static final Logger LOG = LoggerFactory.getLogger(VersionResource.class);
	
	public VersionResource() {
		LOG.debug("VersionResource  00aa10695af5");
	}
	
	@GET
	public String getVersion() {
		return "1.1";
	}

}
