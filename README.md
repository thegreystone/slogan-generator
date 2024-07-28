# slogan-generator

This is a simple slogan text or image generator. I wanted to replace the one I have on my homepage,
since it doesn't support https, and I also wanted the new Raspberry Pi k8s cluster that I've set up
to have something to do. Also, it's my first day of vacation, so why not. :)

## Running the application in dev mode

You can run the application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

Browse to localhost:8080/test to try it out.

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/slogan-generator-1.0.0-SNAPSHOT-runner`

## Creating the docker image
You can create a docker image using:

```shell script
mvnw clean package -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true
```
To create one with a native image:

```shell script
mvnw clean package -Pnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true
```

Run the image using:
```shell script
docker run -i --rm -p 8080:8080 greystone/slogan-generator:latest
```
