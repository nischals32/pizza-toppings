# Favorite Toppings Collector

A simple app that supports collecting favorite toppigs from a user (based on email)

## Description

This app has been built in Kotlin and Spring boot, and allows users to provide their favorite toppings along with their 
email. Some requirements around app behaviour are
1. The app should provide an endpoint to view toppings, and how many unique users have requested them.
2. Only the last toppings submission for the user is saved.
3. Special endpoint for retrieving your own favorite toppings based on YOUR email.

## Getting Started

### Dependencies

* This project uses REDIS as a cache and  database for storage.
* This project includes dependencies defined in gradle including frameworks and testing libraries

### Installing and app startup

1. Clone this project into your local filesystem

2. From the project root folder, run the following command to create the jar
```
./gradlew build
```

3. Start the docker container
```
docker-compose up
```
The docker output should indicate the application has started. 

### Executing program
1. Record your favorite toppings using a tool for API testing(like Postman)
   or, In a new terminal
```
curl --location 'localhost:8080/toppings/' \
--header 'Content-Type: application/json' \
--data-raw '{
"email": "new-user@gmail.com",
"toppings": ["pineapple", "green pepper", "olives"]    
}'
```
2. Verify that the toppings added are now available with the initial counts

```
curl --location 'localhost:8080/toppings/all'
```

3. Let us add some additional toppings with my email (hardcoded in application.properties to my.email@gmail.com
```
curl --location 'localhost:8080/toppings/' \
--header 'Content-Type: application/json' \
--data-raw '{
"email": "my.email@gmail.com",
"toppings": ["mushroom", "olives"]    
}'
```
4. Verify the counts of toppings are now updated
```
curl --location 'localhost:8080/toppings/all'

```
5. Verify the toppings for my email can be seen at the special endpoint
```
curl --location 'localhost:8080/toppings/my-email'

```
6. Update the toppings for a user 
```
curl --location 'localhost:8080/toppings/' \
--header 'Content-Type: application/json' \
--data-raw '{
"email": "new-user@gmail.com",
"toppings": ["pineapple"]    
}'
```
7.  Verify the counts of toppings are now updated
```
curl --location 'localhost:8080/toppings/all'

```
8.  You can also verify the favorite toppings of the user you just updated
```
curl --location 'localhost:8080/toppings/new-user@gmail.com'
```
