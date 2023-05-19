FROM amazoncorretto:17-alpine

# Skapa arbetsmapp och flytta till den
WORKDIR /app

# Kopiera projektets filer
VOLUME /tmp
COPY . /app

# Läs in miljövariabel
ARG DB_PASSWORD
ENV DB_PASSWORD=${DB_PASSWORD}

# Bygg projektet med Gradle
ARG JAR_FILE=build/libs/storeapi_customer.jar
COPY ${JAR_FILE} storeapi_customer.jar
RUN ./gradlew build

# Exponera port och ange startkommando
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "storeapi_customer.jar"]