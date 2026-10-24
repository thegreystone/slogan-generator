#!/bin/bash
set -euo pipefail

if [[ $# -ne 1 ]]; then
    echo "Usage: $0 <version> (for example, $0 0.1.20)" >&2
    exit 1
fi

VERSION="${1#v}"
if [[ ! "$VERSION" =~ ^(0|[1-9][0-9]*)\.(0|[1-9][0-9]*)\.(0|[1-9][0-9]*)$ ]]; then
    echo "Expected a stable version such as 0.1.20 or v0.1.20." >&2
    exit 1
fi

cd "$(dirname "${BASH_SOURCE[0]}")"
if [[ -n "$(git status --porcelain)" ]]; then
    echo "Commit your changes before creating a release tag." >&2
    exit 1
fi

TAG="v$VERSION"
git tag -a "$TAG" -m "Release $VERSION"
git push origin "refs/tags/$TAG"

echo "Pushed $TAG. GitHub Actions will build the release and publish it to Docker Hub and GitHub Releases."
