# 2XB3 Final Project
2XB3 Final Project - Restaurant Recommending Application

This repository contains the Eclipse project for our group's
final project for McMaster's Software 2XB3 Experiential
class. The Eclipse project contains both the client and the server programs.

### Server Setup
1. Ensure that you are running a Unix-based operating system (Linux/macOS), that you have [maven](https://maven.apache.org/) installed and that you are able to execute makefiles
2. Create a new user to run the server with so that your personal files are moderately more secure
3. Download the latest release of [Apache Tomcat](https://tomcat.apache.org/) version 8
4. Extract the folder to the Desktop and rename it `tomcat`
5. Make a copy of the makefile in this repository on your Desktop. The makefile is located in the folder `XB3_Final_Project/src/server`
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
