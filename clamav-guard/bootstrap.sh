#!/bin/sh

# Install dependencies
npm install

# Build the project
npm run build

# Serve the built application
npx serve -s dist -l 3000