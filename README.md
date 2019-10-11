# PHONEBOOK
Backend (JAVA) and Frontend (HTML thymeleaf)   for Web проект “Телефонная книга” project

##Properties file
- for the spring.profile = Json:
```
json.db.path = {path to files}
```

- for the spring.profile = MySql

```
spring.datasource.url = jdbc: mysql: // localhost: 3306 / phonebook? useUnicode = true & characterEncoding = UTF-8 & serverTimezone = UTC
spring.datasource.username = root
spring.datasource.password = root
```

Examples are described in the files:
```
- for spring.profile = MySql: /src/main/resources/prop/application-test_MySql.properties
- spring.profile = Json: /src/main/resources/prop/application-test_JsonDB.properties
```
##MySql: SQL query to create all the necessary tables:

```
create table user
(
    id       varchar(36) not null,
    login    varchar(255),
    password varchar(255),
    pib      varchar(255),
    primary key (id)
);

create table contact 
(
                         id varchar(36) not null,
                         address varchar(255),
                         email varchar(255),
                         first_name varchar(255),
                         home_phone varchar(255),
                         last_name varchar(255) not null,
                         middle_name varchar(255),
                         mobile_phone varchar(255),
                         user_id varchar(36) not null,
                         primary key (id),
                         FOREIGN KEY (user_id) REFERENCES user(Id) ON DELETE CASCADE
);
```

##Building project
JDK 8 need to be installed

From the root dir of a project run

- Mac, Linux: the final Jar file

```mvn clean package```

##Run project

All application settings should be in the properties file,
   the path to which should be passed as arguments
   JVM machine (-Dlardi.conf = / path / to / file.properties):
   Example:
``` 
java -jar -Dlardi.conf = "../ src / main / resources / prop / application-test_JsonDB.properties" ./target/phonebook-1.0.jar
```
##Run project with docker

https://dotsandbrackets.com/persistent-data-docker-volumes-ru/

1.) launch the container in docker-machine:

1.1) open / create container in docker-machine
```
$ docker-machine start phoneDocker - (phoneDocker - container name)
$ docker-machine create phoneDocker - (phoneDocker - container name, default -driver, -d "virtualbox)
$ docker-machine create -d virtualbox phoneDocker - (phoneDocker - container name,
			-driver, -d "virtualbox" Driver to create machine with. [$ MACHINE_DRIVER)
```
1.2) check the list of containers in docker-machine
```
$ docker-machine ls
```
1.3) to get the environment variables that need to be configured to connect to the docker machine:
```
$ docker-machine env phoneDocker
```
1.4) Run this command to configure your shell:
```
# eval $ (docker-machine env phoneDocker)
```
2.) To add an image project to the phoneDocker container

2.1) go to the project directory
```
cd {path to project} /
```
2.2) compile docker image
```
$ mvn clean install package dockerfile: build
```
3.) copy properties file (application-JsonDB.properties):
```
$ docker-machine scp src / main / resources / prop / application-JsonDB.properties phoneDocker: / tmp
```
4.) Ip control (url), state Active, State:
```
$ docker-machine ls
NAME ACTIVE DRIVER STATE URL SWARM DOCKER ERRORS
phoneDocker * virtualbox Running tcp: //192.168.99.100: 2376 v19.03.3
```
5.) work in docker:

5.1) - start of the ssh console (VM starts in parallel)
```
$ docker-machine ssh phoneDocker
```
5.2) - check the running image:
```
$ docker ps
```
5.3) output of all old image-name
```
$ docker ps -a
```
5.4) deleting the old image of all
```
$ docker rm $ (docker ps -a -q -f status = exited)
$ docker rm $ (docker ps -a -q -f status = created)
```
5.5) start container: phoneDocker image: com.lardi / phonebook

- image will listen on port 8080 and accordingly it needs to be set out through -p port: 8080
```
$ docker run -p 8080: 8080 -t com.lardi / phonebook
```
6.) work in the browser (url: tcp: //192.168.99.100: 2376 port: 8080):
```
http://192.168.99.100:8080
```
