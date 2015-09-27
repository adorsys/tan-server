# REST API TAN-Server

## Request register a new Device

`POST /rest/accounts/{accountId}/push-device`
Header: OAuth Authorization: user must match

Response:
SC: 200

	{
		_links: {
			"register-device": "/rest/tans/{userid}/push-device/{registrationId}"
		}
	}

## Activate device registration with a TAN

`POST /rest/accounts/{userid}/push-device/{registrationId}`
Header: OAuth Authorization: user must match

Request Body:

	{
		activationTAN: "12345",
		deviceType: "IOS|ANDROID"
	}
	
SC:201 OK no content
SC:422 invalid TAN


## Check Push Options For user
 
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

Response Body:

SC: 201 Created


	{
		links: {
			consume: "/rest/accounts/userid/tans/pequestId/{tan}"
		}
	}

SC: 401
SC: 400 unavalible Type

## Consume a TAN

`DELETE /rest/accounts/{userid}/tans/{pequestId}/{tan}`
Header: OAuth Authorization: user must match

SC: 204
SC: 401

## Send SMS to a UserId (Request on DIKS System)

`POST /sms/{accountId}`
Header: OAuth Authorization: user must match

Request Body: `text/plain`

	SMS TAN Message

SC: 404 Unknown User
SC: 204 Message was send








