FROM postgres
ENV POSTGRES_USER admin1
ENV POSTGRES_PASSWORD admin1
ENV POSTGRES_DB users1
COPY ./be/postgres.sql /docker-entrypoint-initdb.d/