clean:
	mvn clean

build:
	mvn package

build-no-test:
	mvn package -DskipTests

run: build
	mvn spring-boot:run

run-no-test: build-no-test
	mvn spring-boot:run

dependencies:
	mvn dependency:tree
