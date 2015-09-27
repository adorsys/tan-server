package de.adorsys.tanserver.service;

import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import de.adorsys.tanserver.SystemSettings;

@Singleton
public class SMSService {

	public void sendSMS(String accountId, String oauthToken, String message) throws UnknownAccountException {
		HttpResponse<String> response;
		try {
			response = Unirest.post(SystemSettings.SMS_REST_ENDPOINT)
					  .header("Authorization", "Bearer " + oauthToken)
					  .header("Content-Type", MediaType.TEXT_PLAIN)
					  .body(message).asString();
			if (response.getStatus() == 204) {
				return;
			} else if (response.getStatus() == 404) {
				throw new UnknownAccountException(accountId);
			} else {
				throw new TANServerSystemException(response.getBody());
			}
		} catch (UnirestException e) {
			throw new TANServerSystemException("Problem sending SMS to Account " + accountId, e);
		}
	}

}
