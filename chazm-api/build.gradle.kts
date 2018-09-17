plugins {
    java
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
