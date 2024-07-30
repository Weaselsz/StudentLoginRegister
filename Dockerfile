# Verwenden Sie das Maven-Image für den Build
FROM maven:3.9-amazoncorretto-21-al2023 AS build

# Setzen Sie das Arbeitsverzeichnis innerhalb des Containers
WORKDIR /app

# Kopieren Sie die pom.xml und laden Sie die Maven-Abhängigkeiten herunter
COPY pom.xml .
RUN mvn dependency:go-offline

# Kopieren Sie den Quellcode und bauen Sie das Projekt
COPY src ./src
RUN mvn package -DskipTests

# Verwenden Sie ein schlankes JDK-Image für die Laufzeit
FROM amazoncorretto:21.0.3-al2023

# Setzen Sie das Arbeitsverzeichnis innerhalb des Containers
WORKDIR /app

# Kopieren Sie das JAR-File vom Maven-Container in den aktuellen Container
COPY --from=build /app/target/*.jar app.jar

# Exponieren Sie den Port, den die Anwendung verwendet
EXPOSE 8080

# Definieren Sie den Befehl zum Starten der Anwendung
ENTRYPOINT ["java", "-jar", "app.jar"]