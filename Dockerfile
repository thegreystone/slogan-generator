# Runtime stage
FROM registry.access.redhat.com/ubi8/ubi-minimal:8.4
RUN microdnf install freetype fontconfig \
    && microdnf clean all
WORKDIR /work/
RUN chown 1001 /work \
    && chmod "g+rwX" /work \
    && chown 1001:root /work
COPY --chown=1001:root target/*-runner /work/application

# Set up permissions for user `1001`
RUN chmod 775 /work /work/application \
  && chown -R 1001 /work \
  && chmod -R "g+rwX" /work \
  && chown -R 1001:root /work

# ENV LD_LIBRARY_PATH=/lib64:/usr/lib64

EXPOSE 8080
USER 1001

CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]
