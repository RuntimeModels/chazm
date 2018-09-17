plugins {
    java
    jacoco
    `maven-publish`
}

group = rootProject.group
version = "${rootProject.version}.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_1_10
    targetCompatibility = JavaVersion.VERSION_1_10
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
}

tasks {
    withType(JacocoReport::class) {
        reports {
            xml.isEnabled = true
        }
    }
}
