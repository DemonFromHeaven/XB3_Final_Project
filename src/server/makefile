start:
	~/Desktop/tomcat/bin/catalina.sh start

stop:
	~/Desktop/tomcat/bin/catalina.sh stop

setup:
	cd ~/Desktop && git clone https://github.com/DemonFromHeaven/XB3_Final_Project.git
	rm -r ~/Desktop/tomcat/webapps
	mkdir ~/Desktop/tomcat/webapps
	mkdir ~/Desktop/tomcat/webapps/recommend/
	mkdir ~/Desktop/tomcat/webapps/recommend/WEB-INF
	mkdir ~/Desktop/tomcat/webapps/recommend/WEB-INF/classes
	mkdir ~/Desktop/tomcat/webapps/recommend/WEB-INF/lib

patch:
	cd XB3_Final_Project && git pull && mvn compile && mvn dependency:copy-dependencies
	rsync -aP ~/Desktop/XB3_Final_Project/target/classes/ ~/Desktop/tomcat/webapps/recommend/WEB-INF/classes/
	rsync -aP ~/Desktop/XB3_Final_Project/target/dependency/ ~/Desktop/tomcat/webapps/recommend/WEB-INF/lib/
	cp  ~/Desktop/XB3_Final_Project/src/server/web.xml ~/Desktop/tomcat/webapps/recommend/WEB-INF/web.xml

monitor:
	top
