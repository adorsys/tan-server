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

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.Test;

import com.jayway.restassured.filter.log.ErrorLoggingFilter;
import com.jayway.restassured.response.Response;

import de.adorsys.tanserver.model.TANTransportType;
import de.adorsys.tanserver.rest.to.TANRequestTo;

public class AccountTANSResourceTest extends BaseARTTest {


	@Test
	public void testTanRequest() {
		TANRequestTo tanRequest = new TANRequestTo();
		tanRequest.setRequestId("MyTransaction");
		tanRequest.setTanTransportType(TANTransportType.PUSH_TAN_PREFERED);
		Response tanReqestResponse = given().body(tanRequest).when().post("/accounts/{accountId}/tans", "foobar").then()
				.statusCode(201).and()
				.extract()
				.response();
		String tan = tanReqestResponse.header("x-test-tan");
		assertNotNull("tan", tan);
		String requestIdUrl = tanReqestResponse.header("Location");
		Response requestIdResponse = given().when().get(requestIdUrl).then().statusCode(200).extract().response();
		String consumePath = requestIdResponse.jsonPath().get("links['consume']");
		assertNotNull("consumePath", consumePath);
		
		
		given().when().delete(consumePath.replaceAll("TAN", tan)).then().statusCode(204);
		given().filter(new ErrorLoggingFilter()).when().delete(consumePath.replaceAll("TAN", tan)).then().statusCode(404);
	}

}
