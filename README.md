[![CI](https://github.com/thegreystone/slogan-generator/actions/workflows/ci.yml/badge.svg)](https://github.com/thegreystone/slogan-generator/actions/workflows/ci.yml)
[![Java Version](https://img.shields.io/badge/Java-25%2B-blue)](https://openjdk.org/projects/jdk/25/)
[![Quarkus](https://img.shields.io/badge/Quarkus-3.40.1.1-blue.svg?style=flat&logo=quarkus)](https://quarkus.io/)
[![Docker Pulls](https://img.shields.io/docker/pulls/greystone/slogan-generator.svg)](https://hub.docker.com/r/greystone/slogan-generator)
[![License](https://img.shields.io/badge/License-BSD%203--Clause-blue.svg)](https://opensource.org/licenses/BSD-3-Clause)
[![Version](https://img.shields.io/docker/v/greystone/slogan-generator?sort=semver)](https://hub.docker.com/r/greystone/slogan-generator)

# slogan-generator

This is a simple slogan text or image generator. I wanted to replace the one I used to have on my homepage,
since it doesn't support https, and I also wanted the new Raspberry Pi k8s cluster that I've set up
to have something to do. Also, it's my first day of vacation, so why not. :)

## Using the slogan generator

The slogan generator is up and running at https://api.hirt.se/slogans/test. It is available and ready to use.

The api is described here:  
https://api.hirt.se/slogans/swagger-ui/

Or in OpenAPI format here:  
https://api.hirt.se/slogans/openapi/

Here's an example on how to get a simple slogan text for OpenJDK:  
https://api.hirt.se/slogans/text?item=OpenJDK

Here is an example of how I use it on my homepage https://hirt.se:
```html
<img src="https://api.hirt.se/slogans/image?item=JDK+Mission+Control&background=random&textcolor=%23FFFFFF" style="border: 0;" width="460" height="50" alt="Example slogan image from the hirt.se homepage"/>
```
The slogan generator creates images like these:  
![Slogan Image](docs/images/slogan-java-sunset.png)
![Slogan Image](docs/images/slogan-java-mountain.png)
![Slogan Image](docs/images/slogan-jmc-ocean.png)
![Slogan Image](docs/images/slogan-openjdk-clouds.png)

Here are some examples:
https://api.hirt.se/slogans/test

## Running the application in dev mode

Use JDK 25 or newer, with `JAVA_HOME` pointing to your JDK installation.

You can run the application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

To try it out, Browse to <http://localhost:8080/test>.

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

You can then execute your native executable with: `./target/slogan-generator-(version)-SNAPSHOT-runner`

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

## Publishing images (for maintainers)

Push a stable version tag to create a release:

```shell
git tag -a v0.1.20 -m "Release 0.1.20"
git push origin v0.1.20
```

Commit and push the workflow changes before creating the first release tag. You can also use `bash release.sh 0.1.20` to create and push the tag from a clean working tree.

The release workflow uses JDK 25, derives the Maven version from the tag, and runs the tests. It then publishes:

- Docker Hub images for `linux/amd64` and `linux/arm64`, tagged `greystone/slogan-generator:0.1.20` and `greystone/slogan-generator:latest`.
- A GitHub Release with generated release notes, the multi-platform Docker image digest, a portable fat JAR, and `SHA256SUMS`.

The checked-in Maven version can stay a `-SNAPSHOT`; the release version is set only in the workflow's checkout. Stable version tags such as `v0.1.20` trigger publishing. Prerelease tags such as `v0.1.20-rc.1` do not trigger this workflow.

Pushes to `main` publish `greystone/slogan-generator:edge`. Pull requests build and test the application. The `latest` image follows stable tagged releases.

Configure these repository secrets under **Settings → Secrets and variables → Actions**:

- `DOCKERHUB_USERNAME`: the Docker Hub account with push access to `greystone/slogan-generator`.
- `DOCKERHUB_TOKEN`: that account's Docker Hub access token with write permission.

Image rendering also needs font support: on Linux, install Fontconfig and FreeType. The Docker images include these libraries.

To verify and run a downloaded release with JDK 25 or newer:

```shell
sha256sum -c SHA256SUMS
java -jar slogan-generator-0.1.20.jar
```
