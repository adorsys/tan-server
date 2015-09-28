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
package de.adorsys.tanserver.rest;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.config.ObjectMapperConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.mapper.ObjectMapperType;
import com.jayway.restassured.response.Response;

import de.adorsys.tanserver.model.TANTransportType;
import de.adorsys.tanserver.rest.to.ActivateDeviceRegistrationTo;
import de.adorsys.tanserver.rest.to.TANRequestTo;

import org.junit.Test;

public class AccountTANSResourceTest extends BaseARTTest {


	@Test
	public void testTanRequest() {
		TANRequestTo tanRequest = new TANRequestTo();
		tanRequest.setRequestId("MyTransaction");
		tanRequest.setTanTransportType(TANTransportType.PUSH_TAN_PREFERED);
		Response tanReqestResponse = given().body(tanRequest).when().post("/rest/accounts/{accountId}/tans", "foobar").then().statusCode(200).and().body("links['register-device']", endsWith("rest/rest/accounts/adorsys/push-device/REGID")).extract().response();
		String consumePath = tanReqestResponse.jsonPath().get("links['consume']");
		assertNotNull("consumePath", consumePath);
		String tan = tanReqestResponse.header("x-test-tan");
		assertNotNull("tan", tan);
		
		given().when().post(consumePath.replaceAll("TAN", tan)).then().statusCode(204);
	}

}
