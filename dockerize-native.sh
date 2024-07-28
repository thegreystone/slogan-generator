#!/bin/bash
export DOCKER_BUILDKIT=1
docker buildx create --name multi-arch-builder --use
docker buildx inspect --bootstrap

# Extract version from pom.xml
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

./mvnw package -Pnative
docker buildx build -f Dockerfile.native --platform linux/amd64,linux/arm64 -t greystone/slogan-generator:$VERSION-native -t greystone/slogan-generator:latest-native --push .
