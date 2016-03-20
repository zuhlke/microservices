# Micro-services Demo

An edge service for widgets delegates to a processing service (cluster) to do the work.
Read-only requests use HTTP.  Commands and events use micro-messaging.

![widgets-demo](file://widgets-demo.png)

## Preequisites
- Maven 3
- Redis running locally or in local VM.
- config-service/src/main/resources/application.properties 
  should eb set to 
  spring.cloud.config.server.git.uri=file://<YOUR__PATH_HERE_>/config-repo

## Technologies
### Spring
Boot
Cloud Config
Cloud Sleuth
Actuator
Hateoas
Data Rest
Data Redis

### NetFlix
Eurika
Zuul
Ribbon
Hystrix
Hystrix Console

### Twitter
Zipkin

## Urls

### Edge Service
http://localhost:8050/edge-service/widgets
http://localhost:8050/widgets/names
http://localhost:8050/widgets
http://localhost:8050/widgets [POST] {"action":"MOVE", "from":"Home", "to":"Work", "count": 1 }

### Processing Service
http://localhost:8000/widgets
http://localhost:8000/widgets/search/find?name=Home
http://localhost:8000/configprops
http://localhost:8000/metrics
http://localhost:8000/health

### Discovery Server
http://localhost:8761

### Cloud Config
http://localhost:8888/edge-service/master
http://localhost:8888/processing-service/master

### Hystrix Dashboard
http://localhost:8010/hystrix.html
http://localhost:8050/hystrix.stream

### Zipkin Tracing
http://localhost:9412

### Persistence
http://192.168.99.1:8082/
jdbc:h2:tcp://localhost:8042/./demo


## Known Issues
- A bug in the cloud streaming using Redis means that the DAOs need to be converted manually from JSON to the taregt types.
- The tracing is not currently working when using the micro-messaging between edge and processing services - the traceIds change
  so the full flow is not captured.
- Need to delete the in-memory database when restarting it.
