#!/bin/bash

# Exit immediately if a command exits with a non-zero status.
set -e

echo "Building clamav-backend..."
./mvnw clean install -DskipTests

echo "Starting clamav-backend..."
java -jar target/*.jar