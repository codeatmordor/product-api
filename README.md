# Simple Product API


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
 
 ### APIs
 1. Register User with Product Service
 2. Get Authentication Token
 3. Create Products
 4. Get Products
 5. Update Products
 6. Search Product
 7. Delete Product
     
