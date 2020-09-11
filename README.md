# Enrollment Service

## Prerequisite
`Java-11`
`Maven`
`Docker`
`MySql client`

## Start docker mysql
```
docker run -p 3306:3306 --name=mysql57 -e MYSQL_ROOT_PASSWORD=pwd -d mysql:latest
create database centene
```

## Running the app locally
`mvn spring-boot:run -Dspring-boot.run.profiles=local`

## API 
```
http://<host:port>/swagger-ui.html#
```

