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

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import de.adorsys.tanserver.SystemSettings;
import de.adorsys.tanserver.model.DeviceForAccount;
import de.adorsys.tanserver.model.TANTransportType;
import de.adorsys.tanserver.repository.DeviceForAccountRepository;
import de.adorsys.tanserver.rest.to.ActivateDeviceRegistrationTo;
import de.adorsys.tanserver.rest.to.DeviceRegistrationRequestTo;
import de.adorsys.tanserver.service.InvalidTANException;
import de.adorsys.tanserver.service.TANServerSystemException;
import de.adorsys.tanserver.service.TANService;
import de.adorsys.tanserver.service.UnknownAccountException;
import de.adorsys.tanserver.service.UnsupportedTransportType;

@Path("/accounts/{accountId}/push-device")
@Singleton
public class AccountPushDeviceResource {
	
	private static final String DEVICE_REGISTRATION_REQUESTID = "deviceRegistration";
	
	@Inject
	TANService tanService;
	
	@Inject
	DeviceForAccountRepository deviceRepo;

	@POST
	@Path("{registrationId}")
	public Response registerANewDevice(@PathParam("accountId") String accountId, @PathParam("registrationId") String registrationId,
			ActivateDeviceRegistrationTo deviceRegistration) {
		try {
			tanService.consumeTAN(accountId, DEVICE_REGISTRATION_REQUESTID, deviceRegistration.getActivationTAN());
			
			DeviceForAccount device = new DeviceForAccount();
			device.setAccountId(accountId);
			device.setDeviceRegistrationId(registrationId);
			device.setDeviceType(deviceRegistration.getDeviceType());
			device.setTimestamp(new Date());
			deviceRepo.save(device);
			
			return Response.noContent().build();
		} catch (InvalidTANException e) {
			return Response.serverError().status(422).entity("invalid tan").build();
		}
	}

	@POST
	public Response requestRegisterANewDevice(@HeaderParam("Authorization") String authorization, @PathParam("accountId") String accountId, @Context UriInfo uriInfo) {
		DeviceRegistrationRequestTo deviceRegistrationRequestTo = new DeviceRegistrationRequestTo();
		deviceRegistrationRequestTo.getLinks().put("register-device",
				uriInfo.getBaseUriBuilder().path(AccountPushDeviceResource.class)
						.path(AccountPushDeviceResource.class, "registerANewDevice").build(accountId, "REGID")
						.toString());
		try {
			String tan = tanService.sendGeneratedTan(authorization, accountId, DEVICE_REGISTRATION_REQUESTID, TANTransportType.SMS, SystemSettings.DEVICE_REG_TAN_TEMPLATE);
			ResponseBuilder ok = Response.ok(deviceRegistrationRequestTo);
			if (SystemSettings.TAN_DEV_HEADER) {
				ok.header("x-test-tan", tan);
			}
			return ok.build();
		} catch (UnsupportedTransportType e) {
			throw new TANServerSystemException("Internal Error SMS sould be a supported type", e);
		} catch (UnknownAccountException e) {
			return Response.serverError().status(404).entity("unknown account").build();
		}
	}
}
