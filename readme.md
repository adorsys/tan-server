# Build Instructions

 * git build and clone amp https://github.com/adorsys/amp
 * build tan-server `mvn clean install -DskipTests -PARQ`
 * run it `docker-compose up`

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
	
SC: 404 unknown account

## Activate device registration with a TAN

`POST /rest/accounts/{userid}/push-device/{registrationId}`

Request Body:

	{
		activationTAN: "12345",
		deviceType: "IOS|ANDROID"
	}
	
SC:201 OK no content
SC:422 invalid TAN


## Check push options for user
 
`GET /rest/accounts/{accountId}`
Header: OAuth Authorization: user must match

Response Body

	{
		supportedTypes: [SMS, PUSH_TAN]
	}

## Request a new TAN

`POST /rest/accounts/{accountId}/tans`
Header: OAuth Authorization: user must match

Request Body:
	
	{
		tanTransportType: SMS|PUSH_TAN|PUSH_TAN_PREFERED,
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








