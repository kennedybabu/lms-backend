# Learning Management System

## Overview
- A platform where one can sign up either as an instructor or a student
- one is able to upload courses under different categories and subcategories.
- Student is able to enroll on any course, keep track of their progress
- Instructor is able to see the different metrics on the courses they're offering on the platform

## Features Implemented
* Table Creation
* CRUD Operations
* Primary Keys and Unique constraints
* JOIN operations
* SQL query

## Tech Stack
- Language: Spring Boot 3.0
- Storage: MySQL, Docker
- Demo App: Angular

## Optimization
- Cache the endpoints for faster retrieval of data

## How to Run
./gradlew bootRun

## Challenges Faced
- Serialization of nested objects, tackled this by using @JsonIgnore on the relationships