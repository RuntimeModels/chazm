plugins {
    `build-scan`
    base
    eclipse
    idea

//    id("com.github.kt3k.coveralls") version "2.8.2"
//    id("org.sonarqube") version "2.6.2"
//    id("org.standardout.versioneye") version "1.5.0"

    bintray() apply false
    `gradle-versions-plugin`()
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}

//add(from = "sonarqube.gradle")

//versioneye {
//    includeSubProjects = true
//}

apply(from = "ext.gradle")

//add(from = "coveralls.gradle")

//add(from = "jacocoTestReport.gradle")

tasks {
    wrapper {
        distributionType = Wrapper.DistributionType.ALL
        gradleVersion = Gradle.VERSION
    }
    projectInfo()
}
