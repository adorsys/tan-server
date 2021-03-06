package de.adorsys.tanserver.rest;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Path("/email")
public class EmailMockResource {
	
	private static final Logger LOG = LoggerFactory.getLogger(EmailMockResource.class);

	@POST
	@Path("/{accountId}")
	@Consumes(MediaType.TEXT_PLAIN)
	public void send(@PathParam("accountId") String accountId, String message) {
		LOG.debug("send Email Message to account: {} {}", accountId, message);
	}
}