``` bash
##############################################################
#  ____       _    ____              _   _   _ __  __ ____   #
# / ___|  ___| |_ / ___|__ _ _ __ __| | | | | |  \/  |  _ \  #
# \___ \ / _ \ __| |   / _` | '__/ _` | | | | | |\/| | |_) | #
#  ___) |  __/ |_| |__| (_| | | | (_| | | |_| | |  | |  __/  #
# |____/ \___|\__|\____\__,_|_|  \__,_|  \___/|_|  |_|_|     #
#                                                            #
##############################################################
```

## Build Status
[![Build status](https://travis-ci.org/Nanielito/com.wgu.setcard.ump.svg?master)](https://travis-ci.org/Nanielito)

## Pre-requisites
* Java 8
* Gradle
* MongoDB
* Git 

## Setup

### Install dependencies

### Java 8
Go to Oracle Java page (http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), download and install.

### Gradle
Go to Gradle main page (https://gradle.org/), download and install.

#### MongoDB
Go to MongoDB main page (https://www.mongodb.com/), download and install.

### Git
Go to Git main page (https://git-scm.com/), download and install.

#### To get source code from parent repository
``` bash
> $ git clone https://github.com/nanielito/com.wgu.setcard.ump.git
```

### To build project
``` bash
> $ gradle build
```

### Docker (Alternative)
Docker can be used as an alternative to create instances for database and application core.

Go to Docker main page (https://www.docker.com), install docker engine and docker compose. 

## Development

### To start MongoDB server:         
``` bash
> $ mongod
```

### To create the MongoDB docker container (Alternative):
``` bash
> cd docker
> docker-compose up -d setCard-db
```

### To create the application docker container (Alternative):
``` bash
> cd docker
> docker-compose up -d setCard-ump
```

### To create the application docker container for a development environment (Alternative):
``` bash
> cd docker
> docker-compose up -d setCard-ump-dev
```

### To start, restart and stop docker containers:
``` bash
> docker-compose start CONTAINER
> docker-compose restart CONTAINER
> docker-compose stop CONTAINER
```
Go to Docker Compose page documentation (https://docs.docker.com/compose) to view more details.

* ##### You can use [DockerUtils](https://nanielito.github.io/DockerUtils/) library as a middleware to handle docker-compose commands.

### To start the application server: 
``` bash
> $ gradle bootRun
```

### To preview the app on web browser
Go to http://localhost:8080

## Git Rules

### Commit messages:
Use the following prefix to classified the actions made:
* #N for adding new features.
* #M for updating features.
* #R for removing features or some piece of code.
* #F for fixing some bug on features.

### Branches
Use the following prefix to create branches:
* feature/FUNCTIONALITY-NAME for normal development.
* fix/ISSUE-DESCRIPTION for bug's fix.

## RESTful API resources:

### POST:

* User register:  
  Registers a new user.
  ``` bash
  @ CALL
    URL:PORT/api/v1/public/users/register 
  @ BODY
    { 
      "username": "USERNAME", 
      "password": "PASSWORD"
    }
  @ RETURN
    A string which represents an authentication token:
      eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ...
  ```

* User login:  
  Authenticates a user.
  ``` bash
  @ CALL
    URL:PORT/api/v1/public/users/login 
  @ BODY
    { 
      "username": "USERNAME", 
      "password": "PASSWORD"
    }
  @ RETURN
    A string which represents an authentication token:
      eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ...
  ```

### GET:

* Current user:  
  Gets the current user information.
  ``` bash
  @ CALL
    URL:PORT/api/v1/users/current
  @ HEADERS
    - Authentication: Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ...
  @ RETURN
    A JSON string:
      {
        "username": "USERNAME",
        "password": "PASSWORD",
        ...
      }
  ```

## Version
v0.1.0-SNAPSHOT

## Author
* **DER** - *Initial work* - [nanielito](https://github.com/nanielito)
* **EYT** - *Contributor* - [enriqueyt](https://github.com/enriqueyt)
* **JPI** - *Contributor* - [pitajuan](https://github.com/pitajuan)
* **DMA** - *Contributor* - [deyvidm18](https://github.com/deyvidm18)

## Licence
ISC

