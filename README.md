Authentication Service- 
***********************

Note - All APIs other than signin and signup will require jwt token which we get by signing in with a user and also there is role based accessibility

*********************************************************************************************************************************************************************
1. SignUp endpoint - /auth/signup

Request type - post

curl --location 'http://localhost:8080/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "fullName": "Ravi Raj",
    "email": "rraviraj08211626@gmail.com",
    "password": "123456789",
    "role": "ROLE_RESTAURANT_OWNER"
}' 

Allowed Role - ROLE_CUSTOMER,
    ROLE_RESTAURANT_OWNER,
    ROLE_ADMIN

Sample response - 
{
    "jwt": "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MTI0MzI1OTksImV4cCI6MTcxMjUxODk5OSwiZW1haWwiOiJycmF2aXJhajA4MjExNjI2QGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjoiIn0.u1aB76nwQvdwjPp5VNO2iYxM_uzTUNUJbUsM1UJ_Earx5oJPvM0XcEx25a5YhC928xf3m_H09Sy3dL0PZHglFA",
    "message": "Register success",
    "role": "ROLE_RESTAURANT_OWNER"
}

Response code expected - 201 - Created

Note - Whenever a user sign's up, a cart is assigned to that user untill the user entry is deleted from DB
*********************************************************************************************************************************************************************


2. Signin endpoint - /auth/signin

Request type - post

curl --location 'http://localhost:8080/auth/signin' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "rraviraj08211626@gmail.com",
    "password": "123456789"
}'

Sample Response - 
{
    "jwt": "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MTI0MzMwNDYsImV4cCI6MTcxMjUxOTQ0NiwiZW1haWwiOiJycmF2aXJhajA4MjExNjI2QGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjoiUk9MRV9SRVNUQVVSQU5UX09XTkVSIn0.NZTbldYv612zFUC0dAV3WcBrUC2dVm3GTwUiC_ecDy1asf0gOYJPVhbAF_oIw8PmuSl0sBizvGL3I26UXThZFQ",
    "message": "Signin success",
    "role": "ROLE_RESTAURANT_OWNER"
}

Response Code expected - 200 - OK
*********************************************************************************************************************************************************************




User Service - 
****************

*********************************************************************************************************************************************************************
1. Find User by JWT endpoint - /api/users/profile

Request type - GET

curl --location 'http://localhost:8080/api/users/profile' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MTI0NzQ5MDIsImV4cCI6MTcxMjU2MTMwMiwiZW1haWwiOiJSYXZpQGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjoiUk9MRV9DVVNUT01FUiJ9.Hh8Jhk5pzCcyCQCRL-a7IZMFah_0qaSCLPepYDGEpm7YPOBTuITUxFKleOMwp8UlwED8Ynql23R8zNXDwVbULA'

Sample response - 
{
    "id": 2,
    "fullName": "Ravi User",
    "email": "Ravi@gmail.com",
    "role": "ROLE_CUSTOMER",
    "favourites": [],
    "addresses": []
}

Response code expected - 200

How to use - 
	a. Sign up a new user (not mandatory)
	b. Signin that user
	c. Get jwt from signin and paste it in Authorization in postman 

*********************************************************************************************************************************************************************



Admin Restaurant Service - 
***************************

Note - Roles which are needed to run these APIs - 'ROLE_RESTAURANT_OWNER' & 'ROLE_ADMIN'

******************************************************************************************************************************************************************

1. Create Restaurant 
Endpoint - /api/admin/restaurants

Request Type - Post

curl --location 'http://localhost:8080/api/admin/restaurants' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MTI1MTA2MjAsImV4cCI6MTcxMjU5NzAyMCwiZW1haWwiOiJSYXZpQWRtaW5AZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOiJST0xFX1JFU1RBVVJBTlRfT1dORVIifQ.kxseKXcfm6amw2kXF3GTR0xzPIkkF0XWOPEKH-RLgHX_E7GIHcXnIwDRfWMAbpRCbskMCFLyj_wQyWyaDs5J2Q' \
--data-raw '{
  "name": "Delicious Bites 2",
  "description": "A cozy restaurant serving delicious food",
  "cuisineType": "Italian",
  "address": {
    "streetAddress": "123 Main Street",
    "city": "Siliguri",
    "stateProvince": "WB",
    "postalCode": "734010",
    "country": "India"
  },
  "contactInformation": {
    "mobile": "+1234567890",
    "email": "info@deliciousbites.com",
    "twitter": "@deliciousBites",
    "instagram": "insta@deliciousBites"
  },
  "openingHours": "Mon-Sat: 10:00 AM - 10:00 PM, Sun: 11:00 AM - 9:00 PM",
  "images": [
    "https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FRestaurant&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAE",
    "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pexels.com%2Fsearch%2Frestaurant%2F&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAx"
  ]
}
'

Response code expected - 201

Sample Response - 
{
    "id": 4,
    "owner": {
        "id": 52,
        "fullName": "Ravi Admin",
        "email": "RaviAdmin@gmail.com",
        "role": "ROLE_RESTAURANT_OWNER",
        "favourites": [],
        "addresses": []
    },
    "name": "Delicious Bites 2",
    "description": "A cozy restaurant serving delicious food",
    "cuisineType": "Italian",
    "address": {
        "id": 4
    },
    "contactInformation": {
        "email": "info@deliciousbites.com",
        "mobile": "+1234567890",
        "twitter": "@deliciousBites",
        "instagram": "insta@deliciousBites"
    },
    "openingHours": "Mon-Sat: 10:00 AM - 10:00 PM, Sun: 11:00 AM - 9:00 PM",
    "orders": [],
    "images": [
        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FRestaurant&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAE",
        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pexels.com%2Fsearch%2Frestaurant%2F&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAx"
    ],
    "registrationDate": "2024-04-07T23:01:14.373212",
    "open": false
}

Note - 
	a. There is one to one relationship between user and restaurant and also address and restaurant
	b. We need to use jwt token which we get by signing as admin or restaurant_owner
	c. One user can create only one restaurant and that user is deterined by the jwt token which we pass


******************************************************************************************************************************************************************

2. Delete Restaurant - 

Endpoint - /api/admin/restaurants/{id}

Request Type - Delete

curl --location --request DELETE 'http://localhost:8080/api/admin/restaurants/1' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MTI1MTA2MjAsImV4cCI6MTcxMjU5NzAyMCwiZW1haWwiOiJSYXZpQWRtaW5AZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOiJST0xFX1JFU1RBVVJBTlRfT1dORVIifQ.kxseKXcfm6amw2kXF3GTR0xzPIkkF0XWOPEKH-RLgHX_E7GIHcXnIwDRfWMAbpRCbskMCFLyj_wQyWyaDs5J2Q'

Sample response - 
{
    "message": "Restaurant deleted succesfully"
}

Response code expected - 200

Note - 
	a. There is one to one relationship between user and restaurant and also address and restaurant
	b. We need to use jwt token which we get by signing as admin or restaurant_owner

******************************************************************************************************************************************************************

3. Get Restaurant for a user - 

Endpoint - api/admin/restaurants/user
Request type - Get

curl --location 'http://localhost:8080/api/admin/restaurants/user' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MTI1MTA2MjAsImV4cCI6MTcxMjU5NzAyMCwiZW1haWwiOiJSYXZpQWRtaW5AZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOiJST0xFX1JFU1RBVVJBTlRfT1dORVIifQ.kxseKXcfm6amw2kXF3GTR0xzPIkkF0XWOPEKH-RLgHX_E7GIHcXnIwDRfWMAbpRCbskMCFLyj_wQyWyaDs5J2Q'

Sample response - 
{
    "id": 52,
    "owner": {
        "id": 52,
        "fullName": "Ravi Admin",
        "email": "RaviAdmin@gmail.com",
        "role": "ROLE_RESTAURANT_OWNER",
        "favourites": [],
        "addresses": []
    },
    "name": "Delicious Bites 2",
    "description": "A cozy restaurant serving delicious food",
    "cuisineType": "Italian",
    "address": {
        "id": 52,
        "streetAddress": "123 Main Street",
        "city": "Siliguri",
        "stateProvince": "WB",
        "postalCode": "734010",
        "country": "India"
    },
    "contactInformation": {
        "email": "info@deliciousbites.com",
        "mobile": "+1234567890",
        "twitter": "@deliciousBites",
        "instagram": "insta@deliciousBites"
    },
    "openingHours": "Mon-Sat: 10:00 AM - 10:00 PM, Sun: 11:00 AM - 9:00 PM",
    "orders": [],
    "images": [
        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FRestaurant&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAE",
        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pexels.com%2Fsearch%2Frestaurant%2F&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAx"
    ],
    "registrationDate": "2024-04-07T23:34:39.86603",
    "open": false
}

Response code expected - 200

******************************************************************************************************************************************************************

4. Toggle Heartbeat

Endpoint - api/admin/restaurants/{id}/status
Request type - Put 

curl --location --request PUT 'http://localhost:8080/api/admin/restaurants/52/status' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MTI1MTA2MjAsImV4cCI6MTcxMjU5NzAyMCwiZW1haWwiOiJSYXZpQWRtaW5AZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOiJST0xFX1JFU1RBVVJBTlRfT1dORVIifQ.kxseKXcfm6amw2kXF3GTR0xzPIkkF0XWOPEKH-RLgHX_E7GIHcXnIwDRfWMAbpRCbskMCFLyj_wQyWyaDs5J2Q'

Sample response - 
{
    "id": 52,
    "owner": {
        "id": 52,
        "fullName": "Ravi Admin",
        "email": "RaviAdmin@gmail.com",
        "role": "ROLE_RESTAURANT_OWNER",
        "favourites": [],
        "addresses": []
    },
    "name": "Delicious Bites 2",
    "description": "A cozy restaurant serving delicious food",
    "cuisineType": "Italian",
    "address": {
        "id": 52,
        "streetAddress": "123 Main Street",
        "city": "Siliguri",
        "stateProvince": "WB",
        "postalCode": "734010",
        "country": "India"
    },
    "contactInformation": {
        "email": "info@deliciousbites.com",
        "mobile": "+1234567890",
        "twitter": "@deliciousBites",
        "instagram": "insta@deliciousBites"
    },
    "openingHours": "Mon-Sat: 10:00 AM - 10:00 PM, Sun: 11:00 AM - 9:00 PM",
    "orders": [],
    "images": [
        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FRestaurant&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAE",
        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pexels.com%2Fsearch%2Frestaurant%2F&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAx"
    ],
    "registrationDate": "2024-04-07T23:34:39.86603",
    "open": true
}

Expected response code - 200
******************************************************************************************************************************************************************

5. Update Restaurant

Endpoint - api/admin/restaurants/{id}
Request endpoint - put

curl --location --request PUT 'http://localhost:8080/api/admin/restaurants/52' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MTI1MTA2MjAsImV4cCI6MTcxMjU5NzAyMCwiZW1haWwiOiJSYXZpQWRtaW5AZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOiJST0xFX1JFU1RBVVJBTlRfT1dORVIifQ.kxseKXcfm6amw2kXF3GTR0xzPIkkF0XWOPEKH-RLgHX_E7GIHcXnIwDRfWMAbpRCbskMCFLyj_wQyWyaDs5J2Q' \
--data-raw '{
    "id": 52,
    "owner": {
        "id": 52,
        "fullName": "Ravi Admin",
        "email": "RaviAdmin@gmail.com",
        "role": "ROLE_RESTAURANT_OWNER",
        "favourites": [],
        "addresses": []
    },
    "name": "Delicious Bites 2",
    "description": "A cozy restaurant serving delicious food updated",
    "cuisineType": "Italian",
    "address": {
        "id": 52,
        "streetAddress": "123 Main Street",
        "city": "Siliguri",
        "stateProvince": "WB",
        "postalCode": "734010",
        "country": "India"
    },
    "contactInformation": {
        "email": "info@deliciousbites.com",
        "mobile": "+1234567890",
        "twitter": "@deliciousBites",
        "instagram": "insta@deliciousBites"
    },
    "openingHours": "Mon-Sat: 10:00 AM - 10:00 PM, Sun: 11:00 AM - 9:00 PM",
    "orders": [],
    "images": [
        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FRestaurant&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAE",
        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pexels.com%2Fsearch%2Frestaurant%2F&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAx"
    ],
    "registrationDate": "2024-04-07T23:34:39.86603",
    "open": true
}'

The fields which can be updated - 
	a. cuisine
	b. description
	c. name
	d. opening hours
	e. contact information
******************************************************************************************************************************************************************



Restaurant Service - 
***********************


*************************************************************************************************************************************************************************

1. Get All restaurants -

Endpoint - /api/restaurants
Request type - Get

curl --location 'http://localhost:8080/api/restaurants' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MTI1MTA2MjAsImV4cCI6MTcxMjU5NzAyMCwiZW1haWwiOiJSYXZpQWRtaW5AZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOiJST0xFX1JFU1RBVVJBTlRfT1dORVIifQ.kxseKXcfm6amw2kXF3GTR0xzPIkkF0XWOPEKH-RLgHX_E7GIHcXnIwDRfWMAbpRCbskMCFLyj_wQyWyaDs5J2Q'

Response code expected - 200

Sample Response - 
[
    {
        "id": 52,
        "owner": {
            "id": 52,
            "fullName": "Ravi Admin",
            "email": "RaviAdmin@gmail.com",
            "role": "ROLE_RESTAURANT_OWNER",
            "favourites": [],
            "addresses": []
        },
        "name": "Delicious Bites 2",
        "description": "A cozy restaurant serving delicious food updated",
        "cuisineType": "Italian",
        "address": {
            "id": 52,
            "streetAddress": "123 Main Street",
            "city": "Siliguri",
            "stateProvince": "WB",
            "postalCode": "734010",
            "country": "India"
        },
        "contactInformation": {
            "email": "info@deliciousbites.com",
            "mobile": "+1234567890",
            "twitter": "@deliciousBites",
            "instagram": "insta@deliciousBites"
        },
        "openingHours": "Mon-Sat: 10:00 AM - 10:00 PM, Sun: 11:00 AM - 9:00 PM",
        "orders": [],
        "images": [
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FRestaurant&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAE",
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pexels.com%2Fsearch%2Frestaurant%2F&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAx"
        ],
        "registrationDate": "2024-04-07T23:34:39.86603",
        "open": true
    }
]

**************************************************************************************************************************************************************************

2. Get Restaurant by id

Endpoint - /api/restaurants/{id}
Request type - Get

curl --location 'http://localhost:8080/api/restaurants/52' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MTI1MTA2MjAsImV4cCI6MTcxMjU5NzAyMCwiZW1haWwiOiJSYXZpQWRtaW5AZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOiJST0xFX1JFU1RBVVJBTlRfT1dORVIifQ.kxseKXcfm6amw2kXF3GTR0xzPIkkF0XWOPEKH-RLgHX_E7GIHcXnIwDRfWMAbpRCbskMCFLyj_wQyWyaDs5J2Q'

Sample Response - 
{
    "id": 52,
    "owner": {
        "id": 52,
        "fullName": "Ravi Admin",
        "email": "RaviAdmin@gmail.com",
        "role": "ROLE_RESTAURANT_OWNER",
        "favourites": [],
        "addresses": []
    },
    "name": "Delicious Bites 2",
    "description": "A cozy restaurant serving delicious food updated",
    "cuisineType": "Italian",
    "address": {
        "id": 52,
        "streetAddress": "123 Main Street",
        "city": "Siliguri",
        "stateProvince": "WB",
        "postalCode": "734010",
        "country": "India"
    },
    "contactInformation": {
        "email": "info@deliciousbites.com",
        "mobile": "+1234567890",
        "twitter": "@deliciousBites",
        "instagram": "insta@deliciousBites"
    },
    "openingHours": "Mon-Sat: 10:00 AM - 10:00 PM, Sun: 11:00 AM - 9:00 PM",
    "orders": [],
    "images": [
        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FRestaurant&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAE",
        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pexels.com%2Fsearch%2Frestaurant%2F&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAx"
    ],
    "registrationDate": "2024-04-07T23:34:39.86603",
    "open": true
}

Expected response code - 200
***************************************************************************************************************************************************************************

3. Search restaurants by keyword

Endpoint - api/restaurants/search?keyword=Bites (Query params - keyword)
Request type - get

curl --location 'http://localhost:8080/api/restaurants/search?query=delicious' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MTI1MTA2MjAsImV4cCI6MTcxMjU5NzAyMCwiZW1haWwiOiJSYXZpQWRtaW5AZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOiJST0xFX1JFU1RBVVJBTlRfT1dORVIifQ.kxseKXcfm6amw2kXF3GTR0xzPIkkF0XWOPEKH-RLgHX_E7GIHcXnIwDRfWMAbpRCbskMCFLyj_wQyWyaDs5J2Q'

Sample response if match - 
[
    {
        "id": 52,
        "owner": {
            "id": 52,
            "fullName": "Ravi Admin",
            "email": "RaviAdmin@gmail.com",
            "role": "ROLE_RESTAURANT_OWNER",
            "favourites": [
                {
                    "title": "Delicious Bites 2",
                    "images": [
                        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FRestaurant&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAE",
                        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pexels.com%2Fsearch%2Frestaurant%2F&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAx"
                    ],
                    "description": "A cozy restaurant serving delicious food updated",
                    "id": 52
                }
            ],
            "addresses": []
        },
        "name": "Delicious Bites 2",
        "description": "A cozy restaurant serving delicious food updated",
        "cuisineType": "Italian",
        "address": {
            "id": 52,
            "streetAddress": "123 Main Street",
            "city": "Siliguri",
            "stateProvince": "WB",
            "postalCode": "734010",
            "country": "India"
        },
        "contactInformation": {
            "email": "info@deliciousbites.com",
            "mobile": "+1234567890",
            "twitter": "@deliciousBites",
            "instagram": "insta@deliciousBites"
        },
        "openingHours": "Mon-Sat: 10:00 AM - 10:00 PM, Sun: 11:00 AM - 9:00 PM",
        "orders": [],
        "images": [
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FRestaurant&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAE",
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pexels.com%2Fsearch%2Frestaurant%2F&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAx"
        ],
        "registrationDate": "2024-04-07T23:34:39.86603",
        "open": true
    }
]

If no match - 
[]

Expected response code - 200

Note - The query provided aims to retrieve restaurants whose name or cuisine type contains a given query string, case-insensitively
***************************************************************************************************************************************************************************

4. Add to favourite (Adding a restaurant as favourite for a user)

Endpoint - /api/restaurants/{restaurantId}/add-favourites
Request type - put

curl --location --request PUT 'http://localhost:8080/api/restaurants/52/add-favourites' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MTI1MTA2MjAsImV4cCI6MTcxMjU5NzAyMCwiZW1haWwiOiJSYXZpQWRtaW5AZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOiJST0xFX1JFU1RBVVJBTlRfT1dORVIifQ.kxseKXcfm6amw2kXF3GTR0xzPIkkF0XWOPEKH-RLgHX_E7GIHcXnIwDRfWMAbpRCbskMCFLyj_wQyWyaDs5J2Q'

Expected response - 
{
    "title": "Delicious Bites 2",
    "images": [
        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FRestaurant&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAE",
        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pexels.com%2Fsearch%2Frestaurant%2F&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAx"
    ],
    "description": "A cozy restaurant serving delicious food updated",
    "id": 52
}

Expected status code - 200

***************************************************************************************************************************************************************************

5. Remove from favourites (Remove a restaurant as favourite for a user)

Endpoint - api/restaurants/{restaurantId}/remove-from-favourites
RequestType - put

curl --location --request PUT 'http://localhost:8080/api/restaurants/52/remove-from-favourites' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MTI1MTA2MjAsImV4cCI6MTcxMjU5NzAyMCwiZW1haWwiOiJSYXZpQWRtaW5AZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOiJST0xFX1JFU1RBVVJBTlRfT1dORVIifQ.kxseKXcfm6amw2kXF3GTR0xzPIkkF0XWOPEKH-RLgHX_E7GIHcXnIwDRfWMAbpRCbskMCFLyj_wQyWyaDs5J2Q'

Expected response - 
{
    "title": "Delicious Bites 2",
    "images": [
        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FRestaurant&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAE",
        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pexels.com%2Fsearch%2Frestaurant%2F&psig=AOvVaw1SSD9p2dKEbPIEQIA8fHaI&ust=1712596730001000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDGuJLOsIUDFQAAAAAdAAAAABAx"
    ],
    "description": "A cozy restaurant serving delicious food updated",
    "id": 52
}

Expected status code - 200
