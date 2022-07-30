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
2. Then we need to set up the database. As application using MySql database, we need it to being installed on our local machine. Here is useful article how to set up and run MySql as a docker container: [MySQL Docker Container Tutorial: How to Set Up & Configure](https://phoenixnap.com/kb/mysql-docker-container)
```bash
docker run \
--detach \
--name=[container_name] \
--env="MYSQL_ROOT_PASSWORD=[my_password]" \
--publish 6603:3306 \
--volume=/root/docker/[container_name]/conf.d:/etc/mysql/conf.d \
mysql
```
3. To create the production and test databases, we need to execute the [create_schema.sql](.schema/create_schema.sql) and [create_schema.sql](_schema/create_test_schema.sql) scripts.
4. Now need to update `hibernate.connection.username` and `hibernate.connection.password` values with the local mysql `user` and `password`.

#### Build
##### Maven
```bash
$ mvn -U clean install
$ java -jar target/person-registry.jar
```
##### Gradle
```bash
$ gradle build
$ java -jar build/libs/person-registry-3.0.1.jar
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