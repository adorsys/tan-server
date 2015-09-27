/**
 * 
 */
package de.adorsys.tanserver.rest.to;

/**
 * @author sso
 *
 */
public class ActivateDeviceRegistrationTo {

	public enum DeviceType {
		IOS, ANDROID
	}

	private String activationTAN;
	private DeviceType deviceType;

	public String getActivationTAN() {
		return activationTAN;
	}

	public void setActivationTAN(String activationTAN) {
		this.activationTAN = activationTAN;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

}
