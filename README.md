#API Documentation of vote system

## Default users

### Default admin user

login: yaneg.ru@gmail.com  
password: 123

### Default regular user

login: user@mail.com  
password: 123


## Create regular user

**access:**  
public

**curl:**  

curl --location --request POST 'http://localhost:8080/graduation_of_topjava/users' \
--header 'Content-Type: application/json' \
--data-raw '{
	"firstName":"111",
	"lastName":"222",
	"email":"yaneg12.ru@gmail.com",
	"password":"test_pasword"
}'

**response body:**  

{
    "publicUserId": "XL4h8qconxiUG5t4jOs11v8GxiU7Ps",
    "firstName": "111",
    "lastName": "222",
    "email": "yaneg12.ru@gmail.com"
}


## Login

**access:**  
public

**curl:**

curl --location --request GET 'http://localhost:8080/graduation_of_topjava/users/login' \
--header 'Content-Type: application/json' \
--data-raw '{
	"email":"yaneg12.ru@gmail.com",
	"password":"test_pasword"
}'

**response headers**

--PublicUserID 'XL4h8qconxiUG5t4jOs11v8GxiU7Ps'  
--Authorization 'GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZzEyLnJ1QGdtYWlsLmNvbSJ9.A2PB7_2_kzLnpB7GH0VkPPnTqYg_dWR2hJecGgJPt0Ugsp_w1fdkQArjKj4DhkXT2SdEUWIOvot-s6kMGZJQ_g'



## Get user by PublicId

**access:**  
Admin or Owner 

**curl:**

curl --location --request GET 'http://localhost:8080/graduation_of_topjava/users/XL4h8qconxiUG5t4jOs11v8GxiU7Ps' \
--header 'Authorization: GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZzEyLnJ1QGdtYWlsLmNvbSJ9.A2PB7_2_kzLnpB7GH0VkPPnTqYg_dWR2hJecGgJPt0Ugsp_w1fdkQArjKj4DhkXT2SdEUWIOvot-s6kMGZJQ_g'

**response body:** 

{
    "publicUserId": "XL4h8qconxiUG5t4jOs11v8GxiU7Ps",
    "firstName": "111",
    "lastName": "222",
    "email": "yaneg12.ru@gmail.com"
}

## Update user

**access:**  
Admin or Owner

**curl:**

curl --location --request PUT 'http://localhost:8080/graduation_of_topjava/users/XL4h8qconxiUG5t4jOs11v8GxiU7Ps' \
--header 'Content-Type: application/json' \
--header 'Authorization: GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZzEyLnJ1QGdtYWlsLmNvbSJ9.A2PB7_2_kzLnpB7GH0VkPPnTqYg_dWR2hJecGgJPt0Ugsp_w1fdkQArjKj4DhkXT2SdEUWIOvot-s6kMGZJQ_g' \
--data-raw '{
    "firstName": "Yaneg1",
    "lastName": "Zolotarev2"
}'

**response body:** 

{
    "publicUserId": "XL4h8qconxiUG5t4jOs11v8GxiU7Ps",
    "firstName": "Yaneg1",
    "lastName": "Zolotarev2",
    "email": "yaneg12.ru@gmail.com"
}

## Get all users

**access:**  
Admin

**curl:**

curl --location --request GET 'http://localhost:8080/graduation_of_topjava/users/' \
--header 'Authorization: GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw'

**response body:** 

[
    {
        "publicUserId": "mZt11zvVfTDGmnS1h7CjMDvSWxwpXj",
        "firstName": "Evgeniy",
        "lastName": "Zolotarev",
        "email": "yaneg.ru@gmail.com"
    },
    {
        "publicUserId": "S3sKbzG7kmDCQnei3KiHqcfpcAaCDD",
        "firstName": "FirstName",
        "lastName": "LastName",
        "email": "user@mail.com"
    },
    {
        "publicUserId": "XL4h8qconxiUG5t4jOs11v8GxiU7Ps",
        "firstName": "Yaneg1",
        "lastName": "Zolotarev2",
        "email": "yaneg12.ru@gmail.com"
    }
]

#Eateries

## Get eatery by id

**access:**  
Admin

**curl:**

curl --location --request GET 'http://localhost:8080/graduation_of_topjava/eateries/5' \
--header 'Authorization: GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw'

**response body:** 

{
    "id": 5,
    "name": "FirstEatery"
}

## Get all eatery

**access:**  
Admin

**curl:**

curl --location --request GET 'http://localhost:8080/graduation_of_topjava/eateries/?page=1&limit=5' \
--header 'Authorization: GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw'

**response body:** 

[
    {
        "id": 5,
        "name": "FirstEatery"
    },
    {
        "id": 10,
        "name": "SecondEatery"
    }
]

## Create eatery

**access:**  
Admin

**curl:**

curl --location --request POST 'http://localhost:8080/graduation_of_topjava/eateries/' \
--header 'Authorization: GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw' \
--header 'Content-Type: application/json' \
--data-raw '{
	"name":"TestEatery"
}'

**response body:**

{
    "id": 15,
    "name": "TestEatery"
}

## Update eatery

**access:**  
Admin

**curl:**

curl --location --request PUT 'http://localhost:8080/graduation_of_topjava/eateries/15' \
--header 'Authorization: GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw' \
--header 'Content-Type: application/json' \
--data-raw '{
	"name":"TestEateryUpdated"
}'

**response body:**  

{
    "id": 15,
    "name": "TestEateryUpdated"
}


## Delete eatery

**access:**  
Admin

**curl:**

curl --location --request DELETE 'http://localhost:8080/graduation_of_topjava/eateries/15' \
--header 'Authorization: GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw' \
--header 'Content-Type: application/json' \
--data-raw ''

**response body:**  

{
    "operationResult": "SUCCESS",
    "operationName": "DELETE"
}

# MenuItem

## Get all MenuItem by EateryId and Date

**access:**  
Admin, Regular

**curl:**

curl --location --request GET 'http://localhost:8080/graduation_of_topjava/eateries/5/menu?date=2020-05-09' \
--header 'Authorization: GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw' \
--header 'Content-Type: application/json'

**response body:**  

[
    {
        "id": 6,
        "name": "Пункт меню №3",
        "eatery": {
            "id": 5,
            "name": "ThreeEateryUpd11"
        },
        "date": "2020-05-09",
        "price": 50.0
    },
    {
        "id": 7,
        "name": "Пункт меню №4",
        "eatery": {
            "id": 5,
            "name": "ThreeEateryUpd11"
        },
        "date": "2020-05-09",
        "price": 11.77
    }
]

## Get all MenuItem by Date

**access:**  
Admin, Regular

**curl:**

curl --location --request GET 'http://localhost:8080/graduation_of_topjava/eateries/menu?date=2020-05-09' \
--header 'Authorization: GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw' \
--header 'Content-Type: application/json'

**response body:**

[
    {
        "id": 6,
        "name": "Пункт меню №3",
        "eatery": {
            "id": 5,
            "name": "ThreeEateryUpd11"
        },
        "date": "2020-05-09",
        "price": 50.0
    },
    {
        "id": 7,
        "name": "Пункт меню №4",
        "eatery": {
            "id": 5,
            "name": "ThreeEateryUpd11"
        },
        "date": "2020-05-09",
        "price": 11.77
    },
    {
        "id": 11,
        "name": "Пункт меню №1",
        "eatery": {
            "id": 10,
            "name": "SecondEatery"
        },
        "date": "2020-05-09",
        "price": 90.89
    },
    {
        "id": 12,
        "name": "Пункт меню №2",
        "eatery": {
            "id": 10,
            "name": "SecondEatery"
        },
        "date": "2020-05-09",
        "price": 55.77
    }
]  

## Create MenuItem

**access:**  
Admin

**curl:**

curl --location --request POST 'http://localhost:8080/graduation_of_topjava/eateries/5/menu' \
--header 'Authorization: GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw' \
--header 'Content-Type: application/json' \
--data-raw '{
	"eateryId":"5",
	"date":"2020-05-08",
	"name": "created itemMenu by postman",
	"price": 10.99
}'

**response body:** 

{
    "id": 16,
    "name": "created itemMenu by postman",
    "eatery": {
        "id": 5,
        "name": "ThreeEateryUpd11"
    },
    "date": "2020-05-08",
    "price": 10.99
}

## Update MenuItem

**access:**  
Admin

**curl:**

curl --location --request PUT 'http://localhost:8080/graduation_of_topjava/eateries/5/menu/16' \
--header 'Authorization: GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw' \
--header 'Content-Type: application/json' \
--data-raw '{
	"date":"2020-05-09",
	"name": "created itemMenu by postman",
	"price": 11.99
}'

**response body:**

{
    "id": 16,
    "name": "created itemMenu by postman",
    "eatery": {
        "id": 5,
        "name": "ThreeEateryUpd11"
    },
    "date": "2020-05-09",
    "price": 11.99
}  

## Delete MenuItem

**access:**  
Admin

**curl:**

curl --location --request DELETE 'http://localhost:8080/graduation_of_topjava/eateries/menu/16' \
--header 'Authorization: GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw'

**response body:**

{
    "operationResult": "SUCCESS",
    "operationName": "DELETE"
}  

## Get MenuItem by id

**access:**  
Admin, Regular

**curl:**

curl --location --request GET 'http://localhost:8080/graduation_of_topjava/eateries/menu/12' \
--header 'Authorization: GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw'

**response body:**

{
    "id": 12,
    "name": "Пункт меню №2",
    "eatery": {
        "id": 10,
        "name": "SecondEatery"
    },
    "date": "2020-05-09",
    "price": 55.77
}

#Vote

## Make vote

**access:**  
Admin, Regular

**curl:**

curl --location --request POST 'http://localhost:8080/graduation_of_topjava/eateries/10/votes' \
--header 'Authorization: GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw' \
--header 'Content-Type: application/json'

**response body:**  

{
    "resultOfVote": "Your vote accepted. Thank You",
    "publicUserId": "mZt11zvVfTDGmnS1h7CjMDvSWxwpXj",
    "eateryId": 10,
    "date": "2020-05-07"
}

## Get count votes by EateryId and Date

**access:**  
Admin, Regular

**curl:**

curl --location --request GET 'http://localhost:8080/graduation_of_topjava/eateries/5/votes?date=2020-05-09' \
--header 'Authorization: GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw'

**response body:**

2

##  Get all Votes by Date

**access:**  
Admin, Regular

**curl:**

curl --location --request GET 'http://localhost:8080/graduation_of_topjava/eateries/votes?date=2020-05-09' \
--header 'Authorization: GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw'

**response body:**  

{
    "EateryEntity:5(ThreeEateryUpd11)": 2
}