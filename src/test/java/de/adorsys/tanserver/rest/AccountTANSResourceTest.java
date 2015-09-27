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

import de.adorsys.tanserver.rest.to.ActivateDeviceRegistrationTo;
import de.adorsys.tanserver.rest.to.ActivateDeviceRegistrationTo.DeviceType;
import de.adorsys.tanserver.rest.to.TANRequestTo;
import de.adorsys.tanserver.rest.to.TANTransportType;

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
	}

}
