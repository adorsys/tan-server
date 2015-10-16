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

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import de.adorsys.tanserver.model.DeviceForAccount;
import de.adorsys.tanserver.model.TANTransportType;
import de.adorsys.tanserver.repository.DeviceForAccountRepository;
import de.adorsys.tanserver.rest.to.AccountTanStatusTo;

@Singleton
@Path("/accounts/{acountId}")
public class AccountResource {
	
	@Inject
	DeviceForAccountRepository deviceRepo;
	
	@GET
	public AccountTanStatusTo getStatus(@PathParam("acountId") String  accountId) {
		DeviceForAccount deviceForAccount = deviceRepo.get(accountId);
		AccountTanStatusTo accountTanStatusTo = new AccountTanStatusTo();
		if (deviceForAccount != null) {
			accountTanStatusTo.setSupportedTypes(Arrays.asList(TANTransportType.SMS, TANTransportType.EMAIL, TANTransportType.PUSH_TAN));
		} else {
			accountTanStatusTo.setSupportedTypes(Arrays.asList(TANTransportType.SMS, TANTransportType.EMAIL));			
		}
		return accountTanStatusTo;
	}

}
