package de.adorsys.tanserver.rest;

import java.util.Arrays;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import de.adorsys.tanserver.rest.to.AccountTanStatusTo;
import de.adorsys.tanserver.rest.to.TANTransportType;

@Singleton
@Path("/rest/accounts/{acountId}")
public class AccountResource {
	
	@GET
	public AccountTanStatusTo getStatus(@PathParam("acountId") String  accountId) {
		AccountTanStatusTo accountTanStatusTo = new AccountTanStatusTo();
		accountTanStatusTo.setSupportedTypes(Arrays.asList(TANTransportType.SMS));
		return accountTanStatusTo;
	}

}
