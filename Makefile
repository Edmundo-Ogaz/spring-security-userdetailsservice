clean:
	mvn clean

build:
	mvn package -DskipTests

run: build
	mvn spring-boot:run
