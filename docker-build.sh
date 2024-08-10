cd camunda-integration;
gradle clean build;
docker build -t camunda-integration .;
cd ../jbpm-integration;
gradle clean build;
docker build -t jbpm-integration .;
cd ../frontend;
docker build -t frontend .;
cd ../camunda-engine/my-project
mvn clean install
docker build -t camunda-engine .;
cd ../../main-app;
gradle clean build;
docker build -t main-app .;
cd ..
PROFILE=$1
docker compose -f docker-compose.yaml --profile $PROFILE up -d;

