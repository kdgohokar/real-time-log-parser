# real-time-log-parser

The Java Spring boot application for real-time parsing of the web-server access log to get the count of the various HTTP status code.

### Prerequisites
```
✔ git 2.17.2
✔ java 1.8.0
✔ Apache Maven 3.6.0
```

## Running Locally
```
$ mvn clean install
```
Once successfully compiled jar.
Either execute

```
$ mvn spring-boot:run
```
OR
```
$ java -jar target/realtime-log-parser-0.0.1-SNAPSHOT.jar
```

## API

1. http://localhost:8080/parser/start?fileName=/Users/kd/logs/log.txt

Starts the log parsing in new thread.
Required Parameters:
fileName : Name of the file 

2. http://localhost:8080/parser/stop

Stops the already running log parser.

3. http://localhost:8080/parser/count

Gets the count of the HTTP status code occurred in log file while parsing.

Note: Please see the application logs OR console for ordered representation of the HTTP status code count.
