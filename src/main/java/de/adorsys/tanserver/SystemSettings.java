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
package de.adorsys.tanserver;

public class SystemSettings {

	public static final boolean TAN_DEV_HEADER = Boolean.getBoolean("de.adorsys.tanserver.SystemSettings.TAN_DEV_HEADER");
	public static final String GCM_API_KEY = System.getProperty("de.adorsys.tanserver.SystemSettings.GCM_API_KEY");
	public static final String SMS_REST_ENDPOINT = System.getProperty("de.adorsys.tanserver.SystemSettings.SMS_REST_ENDPOINT");
	public static final String EMAIL_REST_ENDPOINT = System.getProperty("de.adorsys.tanserver.SystemSettings.EMAIL_REST_ENDPOINT");
	public static final String DEVICE_REG_TAN_TEMPLATE = System.getProperty("de.adorsys.tanserver.SystemSettings.DEVICE_REG_TAN_TEMPLATE");
	public static final String TAN_TEMPLATE = System.getProperty("de.adorsys.tanserver.SystemSettings.TAN_TEMPLATE");
	public static final String TAN_TITLE = System.getProperty("de.adorsys.tanserver.SystemSettings.TAN_TITLE");
	public static final String MONGODB_URL = System.getProperty("de.adorsys.tanserver.SystemSettings.MONGODB_URL");
	public static final String DEVICE_REG_TAN_TRANSPORT_TYPE = System.getProperty("de.adorsys.tanserver.SystemSettings.DEVICE_REG_TAN_TRANSPORT_TYPE");

}
