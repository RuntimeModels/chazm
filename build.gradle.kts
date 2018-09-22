plugins {
    `build-scan`
    base
    eclipse
    idea

//    id("com.github.kt3k.coveralls") version "2.8.2"
//    id("org.sonarqube") version "2.6.2"
//    id("org.standardout.versioneye") version "1.5.0"

    id("io.spring.dependency-management") version "1.0.6.RELEASE" apply false
    id("com.jfrog.bintray") version v.bintray apply false
}

buildScan {
    setTermsOfServiceUrl("https://gradle.com/terms-of-service")
    setTermsOfServiceAgree("yes")
}

//apply(from = "sonarqube.gradle")

//versioneye {
//    includeSubProjects = true
//}

apply(from = "ext.gradle")

//apply(from = "subprojects.gradle")

//apply(from = "coveralls.gradle")

//apply(from = "jacocoTestReport.gradle")

apply(from = "printInfo.gradle")

tasks {
    withType(Wrapper::class) {
        distributionType = Wrapper.DistributionType.ALL
        gradleVersion = v.gradle
    }
}