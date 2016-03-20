#!/bin/bash
java -jar lib/zipkin-web-all.jar -zipkin.web.port=:9412 -zipkin.web.query.dest=localhost:9411