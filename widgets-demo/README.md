# Micro-services Demo
Widgets, commands and events.
An edge service for widgets delegates to a processing service (cluster) to do the work.
Read-only requests use HTTP.  Commands and events use micro-messaging.

![widgets-demo](widgets-demo.png)

## Preequisites
- Maven 3
- Rabbit MQ running locally
- config-service/src/main/resources/application.properties 
  for ease of use should be set to 
  spring.cloud.config.server.git.uri=file://<PATH_TO_THIS_DEMO>/config-repo

## Technologies
### Spring
Boot (Application)
Actuator(Metrics)
Hateoas (REST)
Data Rest
Data Jpa
Cloud Config
Cloud Sleuth with Zipkin
Cloud Stream with Rabbit MQ

### NetFlix
Eurika (Service Discovery)
Zuul (Http Proxy)
Ribbon (Dynamic Load Balancer)
Hystrix (Circuit Breaker & Metrics)
Hystrix Console (Monitoring)

### Twitter
Zipkin (Correlated Tracing)

## Urls

### Config Service

- [http://localhost:8888/widgets-edge/master]()
- [http://localhost:8888/widgets-processing/master]()

### Discovery Service
- [http://localhost:8761]()

### Widget Database
- [http://192.168.99.1:8082/]()
jdbc:h2:tcp://localhost:8042/./demo

### Widget Processing Service
- [http://localhost:8000/widgets]()
- [http://localhost:8000/widgets/search/findByName?name=Wendy]()
- [http://localhost:8000/configprops]()
- [http://localhost:8000/metrics]()
- [http://localhost:8000/health]()

### Widget Edge Service
- [http://localhost:8050/widgets]()
- [http://localhost:8050/widgets/names]()
- [http://localhost:8050/transactions]() [POST] {"from":"Bob", "to":"Jack", "count": 1 }
- [http://localhost:8050/configprops]()
- [http://localhost:8050/metrics]()
- [http://localhost:8050/health]()
- [http://localhost:8050/hystrix.stream]()

### Hystrix Dashboard
- [http://localhost:8080/hystrix]()
- [http://localhost:8050/hystrix.stream]()

### Zipkin Tracing
- [http://localhost:9411]()
- [http://localhost:9990/admin]()
