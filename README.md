# person-registry 

#### Description:
This simple console application dedicated & implemented to learn the abilities of hibernate framework.
This project was developed during study in <a href="https://www.it-academy.by">IT-Academy</a> as a training project to learn hibernate framework during java course.
Then lately it was reworked and refactored, keeping the general business idea of the app untouched.

This is a console application, that allows user to manage Person entity, and it's related entities: Address & Department.

System name: **person-registry**

#### Getting Started
1. To run the application, first we need to checkout [winter-io](https://github.com/cyberalexander/winter-io) framework and successfully compile it on our local machine.
It's important, as this application based on [winter-io](https://github.com/cyberalexander/winter-io) framework.
2. Then we need to set up the database. As application using MySql database, we need it to be installed on our local machine. 
3. To create the production and test databases, we need to execute the [create_schema.sql](_schema/create_schema.sql) and [create_schema.sql](_schema/create_test_schema.sql) scripts.
4. Now need to update `hibernate.connection.username` and `hibernate.connection.password` values with the local mysql `user` and `password`.

#### Build:
```bash
$ mvn -U clean install
$ java -jar target/person-registry.jar
```

#### The application menu view:
![img.png](static/menu_v2.0.3.jpg)

#### Tech stack:
- Java 17
- Maven
- Hibernate 5.X
- MySQL 8.X
- JUnit 5
- Lombok
- PMD, CheckStyle
- JaCoCo
- [winter-io](https://github.com/cyberalexander/winter-io) custom framework