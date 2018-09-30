import buildSrc.*

plugins {
    `build-scan`
    base
    eclipse
    idea

//    id("com.github.kt3k.coveralls") version "2.8.2"
//    id("org.sonarqube") version "2.6.2"
//    id("org.standardout.versioneye") version "1.5.0"

    buildSrc.Plugins.Bintray.add(this) apply false
    id("com.github.ben-manes.versions") version "0.20.0"
}

buildScan {
    setTermsOfServiceUrl("https://gradle.com/terms-of-service")
    setTermsOfServiceAgree("yes")
}

//add(from = "sonarqube.gradle")

//versioneye {
//    includeSubProjects = true
//}

apply(from = "ext.gradle")

//add(from = "coveralls.gradle")

//add(from = "jacocoTestReport.gradle")

tasks {
    withType(Wrapper::class) {
        distributionType = Wrapper.DistributionType.ALL
        gradleVersion = buildSrc.Gradle.version
    }
    create("printInfo") {
        doLast {
            println(" Project name: ${project.name}")
            println(" - Group Id: ${project.group}")
            println(" - Major version: ${project.version}")
            println(" - Number of subproject: ${subprojects.size}")
            for (project in subprojects) {
                println("   - Subproject name: ${project.name}:")
                println("     - Group Id: ${project.group}")
                println("     - Version: ${project.version}")
                println("     - Number of archive artifacts: ${project.configurations.archives.allArtifacts.size}")
                for (file in project.configurations.archives.artifacts.files) {
                    println("       - Artifact: ${file.name}")
                }
            }
        }
    }
}