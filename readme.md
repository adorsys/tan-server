# REST API TAN-Server

## Register a new Device

`POST /rest/accounts/{userid}/push-device`
Header: OAuth Authorization: user must match

Response:
SC: 200

	{
		links: {
			"register-device": /rest/tans/{userid}/push-device/{registrationId}
		}
	}

## Register a device

`POST /rest/accounts/{userid}/push-device/{registrationId}`
Header: OAuth Authorization: user must match

Request Body:

	{
		activationTAN: "12345"
	}

## Check Push Options For user
 
`GET /rest/accounts/{userid}/tans`
Header: OAuth Authorization: user must match

Response Body

	{
		supportedTypes: [SMS, PUSH_TAN]
	}

## Request a new TAN

`POST /rest/accounts/{userid}/tans`
Header: OAuth Authorization: user must match

Request Body:

SC: 201

	{
		type: SMS|PUSH_TAN|AUTO|PUSH_TAN_PREFERED
	}


SC: 401
SC: 400 unavalible Type

## Consume a TAN

`DELETE /rest/accounts/{userid}/tans/{tan}`
Header: OAuth Authorization: user must match

SC: 201
SC: 401

## Send SMS to a UserId (Request on DIKS System)

`POST /sms/{userid}`
Header: OAuth Authorization: user must match

Request Body:

	{
		message: "SMS TAN Message"
	}

SC: 404 Unknown User
SC: 400 Unknown Phonenumber for Account
SC: 201 Message was send








