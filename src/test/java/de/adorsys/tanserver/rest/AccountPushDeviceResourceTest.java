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

public class AccountPushDeviceResourceTest extends BaseARTTest {
	
	@Test
	public void testRegisterANewDeviceAndCheckRegistation() {
		Response tanReqestResponse = given().when().post("/rest/accounts/adorsys/push-device").then().statusCode(200).and().body("links['register-device']", endsWith("rest/rest/accounts/adorsys/push-device/REGID")).extract().response();
		String activationPath = tanReqestResponse.jsonPath().get("links['register-device']");
		tanReqestResponse.prettyPrint();
		assertNotNull("activationPath", activationPath);
		String tan = tanReqestResponse.header("x-test-tan");
		assertNotNull("tan", tan);
		
		ActivateDeviceRegistrationTo activateDeviceRegistrationTo = new ActivateDeviceRegistrationTo();
		activateDeviceRegistrationTo.setActivationTAN(tan);
		activateDeviceRegistrationTo.setDeviceType(DeviceType.IOS);
		given().body(activateDeviceRegistrationTo).when().post(activationPath.replaceAll("REGID", "xxxxxxxxx")).then().statusCode(204);
		
		given().when().get("/rest/accounts/{acountId}", "adorsys").then().statusCode(200).body("supportedTypes", contains("SMS", "PUSH_TAN"));
	}

}
