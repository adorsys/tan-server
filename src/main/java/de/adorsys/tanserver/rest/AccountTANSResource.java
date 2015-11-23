/**
 * Copyright (C) 2015 Sandro Sonntag (sso@adorsys.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.adorsys.tanserver.rest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.adorsys.amp.gcm.client.NotRegisteredException;
import de.adorsys.amp.gcm.client.UnknownRegistrationIdException;
import de.adorsys.tanserver.SystemSettings;
import de.adorsys.tanserver.rest.to.TANRequestTo;
import de.adorsys.tanserver.service.InvalidTANException;
import de.adorsys.tanserver.service.TANService;
import de.adorsys.tanserver.service.UnknownAccountException;
import de.adorsys.tanserver.service.UnsupportedTransportType;

@Singleton
@Path("/accounts/{acountId}/tans")
public class AccountTANSResource {

	@Inject
	TANService tanService;

	private static final Logger LOG = LoggerFactory.getLogger(AccountTANSResource.class);

	@POST
	public Response tanRequest(@HeaderParam("Authorization") String authorization, @PathParam("acountId") String accountId, TANRequestTo tanRequest,
			@Context UriInfo uriInfo) {
		try {
			String tan = tanService.sendGeneratedTan(authorization, accountId, tanRequest.getRequestId(), tanRequest.getTanTransportType(),
					SystemSettings.TAN_TEMPLATE);
			ResponseBuilder response = Response.created(uriInfo.getAbsolutePathBuilder().path(tanRequest.getRequestId()).build());
			if (SystemSettings.TAN_DEV_HEADER) {
				LOG.debug("TAN_DEV_HEADER property exists and is true");
				response.header("x-test-tan", tan);
			}
			return response.build();
		} catch (UnsupportedTransportType e) {
			return Response.serverError().status(422).entity("unsupported Transport Type").build();
		} catch (UnknownAccountException e) {
			return Response.serverError().status(404).entity("unknown account").build();
		} catch (UnknownRegistrationIdException | NotRegisteredException e) {
			return Response.serverError().status(400).entity("not registered").build();
		}
	}

	@GET
	@Path("{requestId}")
	public TANRequestTo getTANRequest(@PathParam("acountId") String accountId, @PathParam("requestId") String requestId, @Context UriInfo uriInfo) {
		TANRequestTo tanRequestTo = new TANRequestTo();
		tanRequestTo.getLinks().put("consume", uriInfo.getAbsolutePathBuilder().path("TAN").build());
		return tanRequestTo;
	}

	@DELETE
	@Path("{requestId}/{tan}")
	public Response consumeTAN(@PathParam("acountId") String accountId, @PathParam("requestId") String requestId, @PathParam("tan") String tan,
			@Context UriInfo uriInfo) {
		try {
			tanService.consumeTAN(accountId, requestId, tan);
			return Response.noContent().build();
		} catch (InvalidTANException e) {
			return Response.serverError().status(404).entity("unknown tan").build();
		}
	}

}
