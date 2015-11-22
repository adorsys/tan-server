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

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.adorsys.amp.gcm.client.GCMMessage.GCMNotification;
import de.adorsys.amp.gcm.client.GCMService;
import de.adorsys.amp.gcm.client.NotRegisteredException;
import de.adorsys.amp.gcm.client.UnknownRegistrationIdException;
import de.adorsys.tanserver.SystemSettings;
import de.adorsys.tanserver.model.DeviceForAccount;
import de.adorsys.tanserver.model.TANForAccountAndRequestId;
import de.adorsys.tanserver.model.TANTransportType;
import de.adorsys.tanserver.repository.DeviceForAccountRepository;
import de.adorsys.tanserver.repository.TANForAccountAndRequestIdRepository;

@Singleton
public class TANService {

	private static final Logger LOG = LoggerFactory.getLogger(TANService.class);

	@Inject
	TANForAccountAndRequestIdRepository tanRepo;

	@Inject
	DeviceForAccountRepository deviceForAccountRepository;

	@Inject
	GCMService gcmService;

	@Inject
	SMSService smsService;

	@Inject
	EmailService emailService;

	public String sendGeneratedTan(@Nonnull String authorization, @Nonnull String accountId, @Nonnull String requestId,
			@Nonnull TANTransportType transportType, @Nonnull String template) throws UnsupportedTransportType, UnknownAccountException,
			UnknownRegistrationIdException, NotRegisteredException {
		String tan = RandomStringUtils.random(4, "1234567890");
		TANForAccountAndRequestId tanForAccountAndRequestId = new TANForAccountAndRequestId();
		tanForAccountAndRequestId.setAccountId(accountId);
		tanForAccountAndRequestId.setRequestId(requestId);
		tanForAccountAndRequestId.setTan(tan);
		tanForAccountAndRequestId.setTimestamp(new Date());

		DeviceForAccount deviceForAccount = deviceForAccountRepository.get(accountId);
		String message = MessageFormat.format(template, tan);

		LOG.debug("sending tan to {}", accountId);

		switch (transportType) {
			case PUSH_TAN:
				if (deviceForAccount == null) {
					throw new UnsupportedTransportType(accountId, transportType);
				}
				sendGCMNotification(deviceForAccount, message, tan);
				break;
			case PUSH_TAN_PREFERED:
				if (deviceForAccount != null) {
					sendGCMNotification(deviceForAccount, message, tan);
					break;
				}
			case EMAIL:
				// 4 minutes longer for mail tan
				tanForAccountAndRequestId.setTimestamp(new Date(tanForAccountAndRequestId.getTimestamp().getTime() + (4 * 60 * 1000)));
				sendEmailNotification(authorization, accountId, message, tan);
				break;
			case SMS:
				sendSMSNotification(authorization, accountId, message, tan);
				break;
		}
		tanRepo.save(tanForAccountAndRequestId);
		LOG.debug("sending tan - transportType {}, account {}, requestId {}", transportType, accountId, requestId);

		return tan;
	}

	private void sendSMSNotification(String authorization, String accountId, String message, String tan) throws UnknownAccountException {
		smsService.sendSMS(accountId, authorization, message);
	}

	private void sendEmailNotification(String authorization, String accountId, String message, String tan) throws UnknownAccountException {
		emailService.sendEmail(accountId, authorization, tan);
	}

	private void sendGCMNotification(@Nonnull DeviceForAccount deviceForAccount, @Nonnull String message, @Nonnull String tan)
			throws UnknownRegistrationIdException, NotRegisteredException {
		GCMNotification notification = new GCMNotification();
		notification.setTitle(SystemSettings.TAN_TITLE);
		notification.setBody(message);
		notification.setBadge("1");

		Map<String, Object> data = new HashMap<>();
		data.put("tan", tan);
		try {
			LOG.debug("gcmService {}", gcmService);
			gcmService.sendNotification(notification, data, SystemSettings.GCM_API_KEY, deviceForAccount.getDeviceRegistrationId());
		} catch (UnknownRegistrationIdException | NotRegisteredException e) {
			deviceForAccountRepository.delete(deviceForAccount);
			throw e;
		}
	}

	public void consumeTAN(String accountId, String requestId, String tan) throws InvalidTANException {
		boolean wasFound = tanRepo.deleteTAN(accountId, requestId, tan);
		if (!wasFound) {
			throw new InvalidTANException();
		}
	}

}
