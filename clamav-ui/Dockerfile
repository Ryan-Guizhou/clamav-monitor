# Use a lightweight Nginx image as the base
FROM nginx:alpine

# Copy the UI files into the Nginx web root
COPY . /usr/share/nginx/html

# Expose port 80 for web traffic
EXPOSE 80

# Copy bootstrap script and make it executable
COPY bootstrap.sh .
RUN chmod +x bootstrap.sh

# Start service using bootstrap script
ENTRYPOINT ["./bootstrap.sh"]