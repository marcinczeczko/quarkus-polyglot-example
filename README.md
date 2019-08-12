# Polyglot quarkus

This repository contains a naive implementation that runs R script thanks to the GraalVM

## Prerequisites

* [Maven 3.5.3+](https://maven.apache.org/install.html)
* [GraalVM 19.1.1 CE](https://github.com/oracle/graal/releases)

## How to run

### Setup
Set GraalVM as your JDK
```
export GRAALVM_HOME=<path to your graalvm>
export JAVA_HOME=${GRAALVM_HOME}
export PATH=$JAVA_HOME/bin:$PATH
```
Install native image & R language support for GraalVM
```
gu install native-image
gu install R
export R_HOME=${GRAALVM_HOME}/jre/languages/R
```

### Run as java runner
```
mvn package
java -jar target/polyglot-example-1.0-SNAPSHOT-runner.jar
```

### Run as native image
```
mvn package -Pnative
./target/polyglot-example-1.0-SNAPSHOT-runner
```

### Build & run java docker image
```
docker build -f src/main/docker/Dockerfile.jvm -t quarkus-sample/polyglot-jvm .
docker run -i --rm -p 8080:8080 quarkus-sample/polyglot-jvm
```

### How to access
Open [http://localhost:8080/graph](http://localhost:8080/graph) in your browser. 
First request will take some time, because of R script evaluation.

## What's important
* `quarkus-maven-plugin` configuration has added additional flag for native-image build process (to enable R language)
```
<configuration>
  ...
  <additionalBuildArgs>
    <additionalBuildArg>--language:R</additionalBuildArg>
  </additionalBuildArgs>
</configuration>
```