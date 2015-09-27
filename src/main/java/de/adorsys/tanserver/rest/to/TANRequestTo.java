package de.adorsys.tanserver.rest.to;

public class TANRequestTo extends ResourceTo {

	TANTransportType tanTransportType;
	String requestId;

	public TANTransportType getTanTransportType() {
		return tanTransportType;
	}

	public void setTanTransportType(TANTransportType tanTransportType) {
		this.tanTransportType = tanTransportType;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
