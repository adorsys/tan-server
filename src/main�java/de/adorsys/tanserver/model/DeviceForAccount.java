package de.adorsys.tanserver.model;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("devices")
public class DeviceForAccount {
	@Id
	String accountId;
	
	String deviceRegistrationId;
	
	Date mDate = new Date();

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getDeviceRegistrationId() {
		return deviceRegistrationId;
	}

	public void setDeviceRegistrationId(String deviceRegistrationId) {
		this.deviceRegistrationId = deviceRegistrationId;
	}

	public Date getmDate() {
		return mDate;
	}

	public void setmDate(Date mDate) {
		this.mDate = mDate;
	}
	
}
