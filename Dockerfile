# Use an official Tomcat runtime as a parent image
FROM tomcat:9.0

# Copy the WAR file to the webapps directory of Tomcat
COPY app/build/libs/app.war /usr/local/tomcat/webapps/

# Expose the port on which Tomcat runs
EXPOSE 8080

# Start Tomcat server
CMD ["catalina.sh", "run"]
