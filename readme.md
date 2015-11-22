# Build Instructions

 * git build and clone amp https://github.com/adorsys/amp
 * build tan-server `mvn clean install -DskipTests -PARQ`
 * run it `docker-compose up`
 
# TODOS

 * Security warning in LOG out for TAN_DEV_HEADER in response
 * Input validation
 * OAuth authorization

# REST API TAN-Server

## Request register a new device

`POST /rest/accounts/{accountId}/push-device`

Header: OAuth Authorization: user must match

Response Header:
	
	x-test-tan: if de.adorsys.tanserver.SystemSettings.TAN_DEV_HEADER sysprop is set to true
	
Response Body:
SC: 200

	{
		_links: {
			"register-device": "/rest/tans/{userid}/push-device/{registrationId}"
		}
	}
	
CURL:

	curl -X POST http://docker:8080/tan-server/rest/accounts/sso/push-device -H "accept:application/json" -i
	
	HTTP/1.1 200 OK
	Server: Apache-Coyote/1.1
	x-test-tan: 6793
	Content-Type: application/json
	Transfer-Encoding: chunked
	Date: Wed, 30 Sep 2015 22:34:36 GMT
	
	{"links":{"register-device":"http://docker:8080/tan-server/rest/accounts/sso/push-device/REGID"}}
	
SC: 404 unknown account

## Activate device registration with a TAN

`POST /rest/accounts/{userid}/push-device/{registrationId}`

Request Body:

	{
		activationTAN: "12345",
		deviceType: "IOS|ANDROID"
	}
	
Response:

SC:201 OK no content
SC:422 invalid TAN

CURL:

	curl -X POST http://docker:8080/tan-server/rest/accounts/sso/push-device/XXXXXXXX -H "Content-Type:application/json" -i --data "{\"activationTAN\":\"6262\", \"deviceType\":\"ANDROID\"}"
	
	HTTP/1.1 204 No Content
	Server: Apache-Coyote/1.1
	Date: Thu, 01 Oct 2015 06:40:04 GMT



## Check push options for user
 
`GET /rest/accounts/{accountId}`
Header: OAuth Authorization: user must match

Response Body

	{
		supportedTypes: [SMS, EMAIL, PUSH_TAN]
	}

## Request a new TAN

`POST /rest/accounts/{accountId}/tans`
Header: OAuth Authorization: user must match

Request Body:
	
	{
		tanTransportType: SMS|EMAIL|PUSH_TAN|PUSH_TAN_PREFERED,
		requestId: 'a id for a tanRequest | can be your process or a generated one'
	}

Response Header:
	
	x-test-tan: if de.adorsys.tanserver.SystemSettings.TAN_DEV_HEADER sysprop is set to true
	
Response Body:

SC: 201 Created


	{
		links: {
			consume: "/rest/accounts/userid/tans/pequestId/{tan}"
		}
	}

SC: 404 unknown account
SC: 422 unsupported Transport Type

CURL

	curl -X POST http://docker:8080/tan-server/rest/accounts/sso/tans -H "Content-Type:application/json" -i --data "{\"tanTransportType\":\"PUSH_TAN_PREFERED\", \"requestId\":\"MYTRANSACTION\"}"
	
	HTTP/1.1 201 Created
	Server: Apache-Coyote/1.1
	x-test-tan: 0018
	Location: http://docker:8080/tan-server/rest/accounts/sso/tans/MYTRANSACTION
	Content-Length: 0
	Date: Thu, 01 Oct 2015 06:42:44 GMT

## Consume a TAN

`DELETE /rest/accounts/{userid}/tans/{pequestId}/{tan}`
Header: OAuth Authorization: user must match

SC: 204 tan accepted
SC: 404 tan not known

## Send SMS to a UserId (outgoing request to send a SMS)

`POST /sms/{accountId}`
Header: OAuth Authorization: user must match

Request Body: `text/plain`

	SMS TAN Message

SC: 404 Unknown User
SC: 204 Message was send








