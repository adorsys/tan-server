package de.adorsys.tanserver.rest;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import de.adorsys.tanserver.SystemSettings;
import de.adorsys.tanserver.rest.to.ActivateDeviceRegistrationTo;
import de.adorsys.tanserver.rest.to.DeviceRegistrationRequestTo;

@Path("/rest/accounts/{accountId}/push-device")
@Singleton
public class AccountPushDeviceResource {

	@POST
	@Path("{registrationId}")
	public Response registerANewDevice(@PathParam("accountId") String accountId, @PathParam("registrationId") String registrationId,
			ActivateDeviceRegistrationTo deviceRegistration) {
		return Response.noContent().build();
	}

	@POST
	public Response requestRegisterANewDevice(@PathParam("accountId") String accountId, @Context UriInfo uriInfo) {
		DeviceRegistrationRequestTo deviceRegistrationRequestTo = new DeviceRegistrationRequestTo();
		deviceRegistrationRequestTo.getLinks().put("register-device",
				uriInfo.getBaseUriBuilder().path(AccountPushDeviceResource.class)
						.path(AccountPushDeviceResource.class, "registerANewDevice").build(accountId, "REGID")
						.toString());
		
		ResponseBuilder ok = Response.ok(deviceRegistrationRequestTo);
		if (SystemSettings.TAN_DEV_HEADER) {
			ok.header("x-test-tan", "1234");
		}
		return ok.build();
	}
}
