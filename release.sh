#!/bin/bash
export DOCKER_BUILDKIT=1
docker buildx create --name multi-arch-builder --use
docker buildx inspect --bootstrap

# Extract version from pom.xml
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

./mvnw package

# Only building with the release tag - latest is built on each commit to GitHub
docker buildx build -f src/main/docker/Dockerfile.jvm --platform linux/amd64,linux/arm64 -t greystone/slogan-generator:$VERSION --push .
