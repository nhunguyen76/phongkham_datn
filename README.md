# SXD CSDL Da Nang #

## Domains ##

* Test: http://projects.greenglobal.vn:6782/sxd-csdldn
* Demo: 
* Live: 

## Environment of development ##

* JDK 1.8+
* Maven 3.0+
* MySQL 5.7
* Eclipse for java EE IDE

## Deployment environment ##

* OS: Linux-Ubuntu version x.x.x
* RAM: 4M
* HDD: 500G

## Setup MySQL ##

* https://support.rackspace.com/how-to/installing-mysql-server-on-ubuntu/

## Setup tomcat ##

* https://www.digitalocean.com/community/tutorials/how-to-install-apache-tomcat-8-on-ubuntu-16-04

## Build .war file ##

* mvn clean package

## Deploy ##

* Send .war file to /opt/tomcat8/webapps

## Tomcat's action ##

* Start: service tomcat8 start
* Stop: service tomcat8 stop
* Logs: tail -f /opt/tomcat8/logs/catalina.out
