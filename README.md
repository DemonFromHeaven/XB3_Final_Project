# 2XB3 Final Project
2XB3 Final Project - Restaurant Recommending Application

This repository contains the Eclipse project for our group's
final project for McMaster's Software 2XB3 Experiential
class. The Eclipse project contains both the client and the server programs.

### Server Setup
1. Create a new user to run the server with so that your personal files are moderately more secure
2. On a Unix-based operating system (Linux/macOS) Download the latest release of Apache Tomcat version 8
3. Ensure that you have maven installed and that you are able to run makefiles
4. Extract the folder to the Desktop and rename it `tomcat`
5. Copy the makefile in `~/Desktop/XB3_Final_Project/src/server` to the Desktop
6. Run `make setup`
7. Run `make patch`
8. Copy the yelp dataset files into `~/Desktop/tomcat/webapps/recommend/META-INF/classes/data`
9. Run `make start`. The server should take 3 minutes to start.

### Maven
This project uses Maven to build and handle dependencies.

To compile class files:
`mvn compile`

To run tests:
`mvn test`
