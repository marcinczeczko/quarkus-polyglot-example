# Polyglot quarkus

This repository contains a naive implementation that runs R script thanks to the GraalVM

## Prerequisites

* [Maven 3.5.3+](https://maven.apache.org/install.html)
* [GraalVM RC14+ CE](https://github.com/oracle/graal/releases)

## How to run

### Setup
Set GraalVM as your JDK
```
export GRAALVM_HOME=<path to your graalvm>
export JAVA_HOME=${GRAALVM_HOME}
export PATH=$JAVA_HOME/bin:$PATH
```
Install R language support for GraalVM
```
gu install R
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

### Run as native docker image
Create a builder image
```
docker build -f src/main/docker/Dockerfile.mvn -t quarkus/graalvm-rc14-rlang .
```

Build a native image using docker container

```
mvn package -Pnative -Dnative-image.docker-build=quarkus/graalvm-rc14-rlang
```

Finally, build a target image with compiled native executable and run it
```
docker build -f src/main/docker/Dockerfile.native -t quarkus-sample/polyglot-native .
docker run -i --rm -p 8080:8080 quarkus-sample/polyglot-native
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