export JAVA_HOME=/usr/java
export TOMCAT_HOME=/home/yw/apache-tomcat-6.0.43
export OM_HOME=/home/yw/Bottle_CloudServerDeploy
export PATH=$PATH:$TOMCAT_HOME/bin:$JAVA_HOME/bin:
export M2_HOME=/home/yw/apache-maven-3.2.3
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$M2:$PATH

rm $TOMCAT_HOME/logs/*
rm -r $TOMCAT_HOME/work/*
pkill -9 java
mvn clean package

mv target/*.war /home/yw/Bottle_CloudServer.zip
rm -rf $OM_HOME
mkdir $OM_HOME
mkdir $OM_HOME/web
unzip /home/yw/Bottle_CloudServer.zip -d $OM_HOME/web

nohup startup.sh
tail -f $TOMCAT_HOME/logs/catalina.out

