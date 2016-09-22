export JAVA_HOME=/usr/java/latest/
export TOMCAT_HOME=/usr/local/apache-tomcat-6.0.43/
export OM_HOME=/home/om/Bottle_CloudServerDeploy
export PATH=$PATH:$TOMCAT_HOME/bin:$JAVA_HOME/bin:

rm $TOMCAT_HOME/logs/*
rm -r $TOMCAT_HOME/work/*
mvn clean package

mv target/*.war /home/om/Bottle_CloudServer.zip
rm -rf $OM_HOME
mkdir $OM_HOME/web
unzip /home/om/Bottle_CloudServer.zip -d $OM_HOME/web

