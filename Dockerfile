# ========================================
# Étape 1 : Build avec Maven
# ========================================
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copier le pom.xml en premier pour utiliser le cache Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier le code source
COPY src ./src

# Build l'application (skip tests pour build plus rapide)
RUN mvn clean package -DskipTests

# ========================================
# Étape 2 : Runtime léger
# ========================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Créer un utilisateur non-root pour la sécurité
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copier le JAR depuis l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Exposer le port (Render utilise $PORT)
EXPOSE 8080

# Optimisations JVM pour conteneur
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Commande de démarrage
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]