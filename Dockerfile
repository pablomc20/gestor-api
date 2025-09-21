# Usa una imagen base de OpenJDK 21
FROM openjdk:21-jdk-slim

# Argumento para la ruta del JAR
ARG JAR_FILE=target/*.jar

# Copia el archivo JAR de la aplicación al contenedor
COPY ${JAR_FILE} app.jar

# Expone el puerto 8080, que es el puerto por defecto de Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java","-jar","/app.jar"]
