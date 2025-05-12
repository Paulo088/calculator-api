plugins {
	java
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.paulo"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-logging")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")
	implementation("org.springdoc:springdoc-openapi-starter-common:2.8.6")
	implementation("io.github.dibog:cloudwatch-logback-appender:2.0.0")
	implementation("ch.qos.logback:logback-classic:1.5.13")
	implementation("ch.qos.logback:logback-core:1.5.13")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.mockito:mockito-core")
	testImplementation("org.mockito:mockito-junit-jupiter")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.mockito")
	}
	testImplementation("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.test {
	useJUnitPlatform()
}
