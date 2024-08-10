cd /home/bogdan/thesis/01-tutorial-first-business-application/business-application-kjar;
mvn install;
container_id=$(docker ps --filter "ancestor=jboss/jbpm-server-full:latest" --format "{{.ID}}");
docker cp /home/bogdan/thesis/01-tutorial-first-business-application/business-application-kjar $container_id:/home/jboss/application;
docker cp /home/bogdan/.m2/repository/com/company/business-application-kjar $container_id:/opt/jboss/.m2/repository/com/company/business-application-kjar;