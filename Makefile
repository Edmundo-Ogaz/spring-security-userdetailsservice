clean:
	mvn clean

build:
	mvn package -DskipTests

run:
	mvn spring-boot:run