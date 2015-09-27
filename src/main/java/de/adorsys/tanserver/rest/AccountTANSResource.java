package de.adorsys.tanserver.rest;

import java.util.Arrays;

import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.adorsys.tanserver.rest.to.AccountTanStatusTo;
import de.adorsys.tanserver.rest.to.TANRequestTo;
import de.adorsys.tanserver.rest.to.TANTransportType;

@Singleton
@Path("/rest/accounts/{acountId}/tans")
public class AccountTANSResource {
	
	@GET
	public AccountTanStatusTo getStatus(@PathParam("acountId") String  accountId) {
		AccountTanStatusTo accountTanStatusTo = new AccountTanStatusTo();
		accountTanStatusTo.setSupportedTypes(Arrays.asList(TANTransportType.SMS));
		return accountTanStatusTo;
	}
	
	@POST
	public Response tanRequest(@PathParam("acountId") String  accountId, TANRequestTo tanRequest, @Context UriInfo uriInfo) {
		return Response.created(uriInfo.getAbsolutePathBuilder().path(tanRequest.getRequestId()).build()).build();
	}
	
	@GET
	@Path("/rest/accounts/{acountId}/tans/{requestId}")
	public TANRequestTo getTANRequest(@PathParam("acountId") String accountId, @PathParam("requestId") String requestId, @Context UriInfo uriInfo) {
		TANRequestTo tanRequestTo = new TANRequestTo();
		tanRequestTo.getLinks().put("consume", uriInfo.getAbsolutePathBuilder().path("TAN").build());
		return tanRequestTo;
	}
	
	@DELETE
	@Path("/rest/accounts/{acountId}/tans/{requestId}/{tan}")
	public Response consumeTAN(@PathParam("acountId") String accountId, @PathParam("requestId") String requestId, @PathParam("requestId") String tan, @Context UriInfo uriInfo) {
		return Response.noContent().build();
	}

}
