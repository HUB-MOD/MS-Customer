FROM openjdk:8-jdk-alpine
ADD target/*.jar /usr/share/ms-customer.jar
EXPOSE 8093
ENTRYPOINT ["java", "-jar", "/usr/share/ms-customer.jar"]