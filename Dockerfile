# --- ETAP 1: Build (Budowanie Aplikacji) ---
# Używamy obrazu z Mavenem i JDK 17. Nadajemy mu alias 'build'.
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# KROK OPTYMALIZACJI: Kopiujemy TYLKO pom.xml
# Dzięki temu Docker zcache'uje wszystkie zależności.
# Jeśli zmienisz kod, ale nie pom.xml, ten etap (pobieranie internetu) się nie powtórzy.
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Dopiero teraz kopiujemy kod źródłowy
COPY src ./src

# Budujemy plik JAR (pomijamy testy, bo to środowisko produkcyjne/deployment)
RUN mvn clean package -DskipTests

# --- ETAP 2: Runtime (Uruchomienie) ---
# Używamy lekkiego obrazu JRE Alpine (tylko to, co potrzebne do uruchomienia)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# BEZPIECZEŃSTWO: Tworzymy użytkownika systemowego 'spring',
# aby nie uruchamiać aplikacji jako root (dobre praktyki security).
RUN addgroup -S spring && adduser -S spring -G spring

# Kopiujemy skompilowany plik JAR z etapu pierwszego ('build')
# Używamy wildcard *.jar, żeby nie martwić się o wersję w nazwie pliku
COPY --from=build /app/target/*.jar app.jar

# Przekazujemy własność pliku użytkownikowi spring
RUN chown spring:spring app.jar

# Przełączamy się na bezpiecznego użytkownika
USER spring:spring

# Wystawiamy port (informacyjnie)
EXPOSE 8080

# Komenda startowa
# -XX:+UseContainerSupport pozwala Javie widzieć limity RAMu kontenera
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "app.jar"]