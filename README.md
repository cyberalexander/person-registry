# Hibernate DEMO 


This sample DEMO service is dedicated to show the basic abilities of hibernate framework
This project was used in <a href="https://www.it-academy.by">IT-Academy</a> as an example to learn hibernate framework during java course.

System name: **hibernate-academy-example**

## Getting Started
build:
```bash
$ mvn -U clean install
```

run component tests:
```bash
$ mvn failsafe:integration-test failsafe:verify
```
run service:
```bash
$ mvn -pl billing-integration-service-application spring-boot:run -Dspring.profiles.active=development
```
