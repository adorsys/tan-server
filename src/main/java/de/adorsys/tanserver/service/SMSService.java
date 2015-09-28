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
package de.adorsys.tanserver.service;

import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import de.adorsys.tanserver.SystemSettings;

@Singleton
public class SMSService {

	public void sendSMS(String accountId, String authorization, String message) throws UnknownAccountException {
		HttpResponse<String> response;
		try {
			response = Unirest.post(SystemSettings.SMS_REST_ENDPOINT)
					  .header("Authorization", authorization)
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
