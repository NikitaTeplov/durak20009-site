FROM gradle:8.11.1-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle installDist --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/install/kotlin-full-groove-room ./
ENV PORT=8080
EXPOSE 8080
CMD ["./bin/kotlin-full-groove-room"]
