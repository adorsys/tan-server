package de.adorsys.tanserver;

public class SystemSettings {
	
	public static final boolean TAN_DEV_HEADER = Boolean.getBoolean("de.adorsys.tanserver.SystemSettings.TAN_DEV_HEADER");
	public static final String GCM_API_KEY = System.getProperty("de.adorsys.tanserver.SystemSettings.GCM_API_KEY");
	public static final String SMS_REST_ENDPOINT = System.getProperty("de.adorsys.tanserver.SystemSettings.SMS_REST_ENDPOINT");

}
