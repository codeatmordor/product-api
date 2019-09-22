# Simple Product API

## Main Design Decisions
1. Batch APIs are implemented for Creation of Product and Updation of product, there is a configurable limit of number of products these APIs accept in request body. Default limit is of 1000 and it can be changed by bulk.request.limit property in application.properties file.
2. JPA Specification is used to dynamically create criterias for searching.
3. Pageable inteface is used for providing pagination.
4. Product color value is limit to [BLUE, GREEN, WHITE, BLACK, ORANGE, RED, YELLOW] Only.
5. JWT based authentication is used for token-based API security.

![alt text](https://github.com/codeatmordor/product-api/blob/master/product/documentation/JWT-based-auth.png)

6. Token expires after 5 hours.
7. Windows based nanoserver image is used for docker base-image.
8. Port 8086 is exposed for docker container


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
 7. Import Postman Collection from /postman collection/GFG Product.postman_collection.json to Postman
 8. Use APIs under GFG Product 
 
 
 ## APIs
 1. Register User with Product Service
 2. Get Authentication Token
 3. Create Products
 4. Get Products
 5. Update Products
 6. Search Product
 7. Delete Product
 
 ## API Documentation:
 APIs are property documented using Postman and documentation can be accessed after importing collection to PostMan and Using 'View in Web' Option
     
