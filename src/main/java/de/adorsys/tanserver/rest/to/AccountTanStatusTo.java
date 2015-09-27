package de.adorsys.tanserver.rest.to;

import java.util.ArrayList;
import java.util.Collection;

public class AccountTanStatusTo {
	private Collection<TANTransportType> supportedTypes = new ArrayList<>();

	public Collection<TANTransportType> getSupportedTypes() {
		return supportedTypes;
	}

	public void setSupportedTypes(Collection<TANTransportType> supportedTypes) {
		this.supportedTypes = supportedTypes;
	}
}
