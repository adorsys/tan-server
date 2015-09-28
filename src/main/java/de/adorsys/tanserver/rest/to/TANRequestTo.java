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
package de.adorsys.tanserver.rest.to;

import de.adorsys.tanserver.model.TANTransportType;

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
