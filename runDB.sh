#!/bin/bash

docker volume create data

docker run -d --name lf9.mariadb \
-v data:/var/lib/mysql \
-p 3306:3306 \
--env MARIADB_USER=lf9 \
--env MARIADB_PASSWORD=lf9 \
--env MARIADB_ROOT_PASSWORD=lf9root \
mariadb:latest