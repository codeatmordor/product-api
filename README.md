# Simple Product API

## Main Design Decisions
1. Batch APIs are implemented for Creation of Product and Updation of product, there is a configurable limit of number of products these APIs accept in request body. Default limit is of 1000 and it can be changed by bulk.request.limit property in application.properties file.
2. JPA Specification is used to dynamically create criterias for searching.
3. Pageable inteface is used for providing pagination.
4. Product color value is limit to [BLUE, GREEN, WHITE, BLACK, ORANGE, RED, YELLOW] Only.
5. JWT based authentication is used for token-based API security.

![alt text](https://github.com/codeatmordor/product-api/blob/master/product/documentation/JWT-based-auth.png)

6. Token expires after *5 hours*.
7. Windows based *nanoserver* image is used for docker base-image.
8. Port 8086 is exposed for docker container
9. For persistance of User and Products in memory *H2 Database* is used.


## Build and Run Instructions

### Dependencies
 1. Java 8
 2. Maven
 3. Postman
 
 ### Steps to Build and Run
 1. cd ./product 
 2. mvn clean install
 3. Open Powershell on windows
 4. cd /product
 5. Use below Docker Commands to build docker image of our product service
 - **Build Docker Image:** 
     ```bash
     docker build -t productservice:v1.0 .
     docker run -d -p 8086:8086 productservice:v1.0
   ```

 6. Product service now running on http://localhost:8086/
 7. Import Postman Collection from */postman collection/GFG Product.postman_collection.json* to Postman
 8. Use APIs under GFG Product 
 
 
 ## APIs
### 1. Register User with Product Service
   ```bash
   curl --location --request POST "http://localhost:8086/gfg/v1/register" \
  --header "Content-Type: application/json" \
  --data "{
  	\"username\":\"gaurav\",
	  \"password\":\"pass@123\"
  }
   ```
### 2. Get Authentication Token
   ```bash
 curl --location --request POST "http://localhost:8086/gfg/v1/authenticate" \
  --header "Content-Type: application/json" \
  --data "{
	\"username\":\"gaurav\",
	\"password\":\"pass@123\"
 }
 ```
 
### 3. Create Products
 ```bash
 curl --location --request POST "http://localhost:8086/gfg/v1/products" \
  --header "Content-Type: application/json" \
  --header "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnYXVyYXYiLCJleHAiOjE1NjkxNDQ4NDQsImlhdCI6MTU2OTEyNjg0NH0.icpNugho6CrExgPvYN0eA4uyWXbLXwqbWAsWXMwpluqPcoo_kpJQEleEwySpa0cl7-cOtXxfoqZ3xC6r5BJMPw" \
  --data "[{
	\"title\":\"phone1\",
	\"description\":\"New Phone1\",
	\"color\":\"RED\",
  \"price\":200,
  \"brand\":\"samsung\"
},{
	\"title\":\"phone2\",
	\"description\":\"New Phone2\",
	\"color\":\"BLUE\",
  \"price\":300,
  \"brand\":\"samsung\"
}]
 ```
 
### 4. Get Product
  ```bash
 curl --location --request GET "http://localhost:8085/gfg/v1/products/2c9820816d54e637016d54e681440000" \
  --header "Content-Type: application/json" \
  --header "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnYXVyYXYiLCJleHAiOjE1NjkxMDQ4NTYsImlhdCI6MTU2OTA4Njg1Nn0.ZG6OD8tEIsTJQ836ThVOUsTj0OvSV0o35n6PuNIxiAt4hdwTVYIYGTUWb2RUs9PafyK_jnwaPe-izyNgCrzXxw" \
  --data "{
  }
  ``` 
    
    
### 5. Update Product
 ```bash
 curl --location --request PUT "http://localhost:8085/gfg/v1/products/2c9820816d554d14016d554d4a390000" \
  --header "Content-Type: application/json" \
  --header "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnYXVyYXYiLCJleHAiOjE1NjkxMDQ5NzMsImlhdCI6MTU2OTA4Njk3M30.Jatct6zMErLzoVMhalUaT4m9MYqmYfC9jixnDn6mk4ngh3iGOzeOkQmD4lKakshxgci3urYHwHxmIzQBvEhyVA" \
  --data "  {
        \"productId\": \"2c9820816d554d14016d554d4a390000\",
        \"title\": \"phone1\",
        \"description\": \"New Phone11\",
        \"color\": \"RED\",
        \"price\": 200,
        \"brand\": \"samsung\"
    }
  ``` 
    
    
### 6. Update Products
  ```bash
 curl --location --request PUT "http://localhost:8085/gfg/v1/products/bulk" \
  --header "Content-Type: application/json" \
  --header "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnYXVyYXYiLCJleHAiOjE1NjkxMDQ5NzMsImlhdCI6MTU2OTA4Njk3M30.Jatct6zMErLzoVMhalUaT4m9MYqmYfC9jixnDn6mk4ngh3iGOzeOkQmD4lKakshxgci3urYHwHxmIzQBvEhyVA" \
  --data "  [{
        \"productId\": \"2c9820816d554d14016d554d4a390000\",
        \"title\": \"phone1\",
        \"description\": \"New Phone11\",
        \"color\": \"RED\",
        \"price\": 200,
        \"brand\": \"samsung\"
    },{
        \"productId\": \"2c9820816d554d14016d554d4a390001\",
        \"title\": \"phone2\",
        \"description\": \"New Phone12\",
        \"color\": \"BLUE\",
        \"price\": 200,
        \"brand\": \"samsung\"
    }]
  ``` 
    
    
### 7. Delete Product
  ```bash
 curl -X DELETE \
  http://localhost:8086/gfg/v1/products/2c9820816d575efd016d575f31110002 \
  --header "Content-Type: application/json" \
  --header "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnYXVyYXYiLCJleHAiOjE1NjkxNDUyNTEsImlhdCI6MTU2OTEyNzI1MX0.hr_z5uISsW8jbU6bvaDqgLMUpgFeJ9E2ngw7hxkIuG0l4pJkOL9-RsU4Ui6H3yjUU4QBbyU6e07spvOsvaF8oQ
  ``` 
    
    
### 8. Search Product By Title
   ```bash
curl -X GET \
  http://localhost:8085/gfg/v1/products?where=%28%20title=%20phone1%20%29 \
   --header "Content-Type: application/json" \
   --header "Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnYXVyYXYiLCJleHAiOjE1NjkxMDQ4NTYsImlhdCI6MTU2OTA4Njg1Nn0.ZG6OD8tEIsTJQ836ThVOUsTj0OvSV0o35n6PuNIxiAt4hdwTVYIYGTUWb2RUs9PafyK_jnwaPe-izyNgCrzXxw
  ``` 
    
    
### 9. Search Product By Description
   ```bash
 curl -X GET \
   http://localhost:8085/gfg/v1/products?where=%28%20description=%20New%20Phone1%20%29 \
   --header "Content-Type: application/json" \
   --header "Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnYXVyYXYiLCJleHAiOjE1NjkxMDQ4NTYsImlhdCI6MTU2OTA4Njg1Nn0.ZG6OD8tEIsTJQ836ThVOUsTj0OvSV0o35n6PuNIxiAt4hdwTVYIYGTUWb2RUs9PafyK_jnwaPe-izyNgCrzXx
  ``` 
    
    
### 10. Sort product by price
   ```bash
 curl -X GET \
   http://localhost:8085/gfg/v1/products?sortby=price&include_count=true&where=%28%20brand=%20samsung%29 \
   --header "Content-Type: application/json" \
   --header "Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnYXVyYXYiLCJleHAiOjE1NjkxMDQ4NTYsImlhdCI6MTU2OTA4Njg1Nn0.ZG6OD8tEIsTJQ836ThVOUsTj0OvSV0o35n6PuNIxiAt4hdwTVYIYGTUWb2RUs9PafyK_jnwaPe-izyNgCrzXx
  ``` 
    
    
### 11. Pagination Request
   ```bash
 curl -X GET \
   http://localhost:8086/gfg/v1/products?limit=10&offset=0&sortby=price&include_count=true&where=%28%20brand=%20samsung%29 \
   --header "Content-Type: application/json" \
   --header "Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnYXVyYXYiLCJleHAiOjE1NjkxMDQ4NTYsImlhdCI6MTU2OTA4Njg1Nn0.ZG6OD8tEIsTJQ836ThVOUsTj0OvSV0o35n6PuNIxiAt4hdwTVYIYGTUWb2RUs9PafyK_jnwaPe-izyNgCrzXx
  ``` 
    
    
 
 ## API Documentation:
 APIs are property documented using Postman and documentation can be accessed after importing collection to PostMan and Using 'View in Web' Option
     
