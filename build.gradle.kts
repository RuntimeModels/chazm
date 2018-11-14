plugins {
    `build-scan`
    base
    eclipse
    idea

//    ID("com.github.kt3k.coveralls") version "2.8.2"
//    ID("org.sonarqube") version "2.6.2"
//    ID("org.standardout.versioneye") version "1.5.0"

    `bintray-version` apply false
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
    wrapper<Wrapper> {
        distributionType = Wrapper.DistributionType.ALL
        gradleVersion = Gradle.VERSION
    }
    register("printInfo") {
        doLast {
            println(" Project name: ${project.name}")
            println(" - Group Id: ${project.group}")
            println(" - Major version: ${project.version}")
            println(" - Number of subproject: ${subprojects.size}")
            subprojects.forEach { project ->
                println("   - Subproject name: ${project.name}:")
                println("     - Group Id: ${project.group}")
                println("     - Version: ${project.version}")
                println("     - Number of archive artifacts: ${project.configurations.archives.get().allArtifacts.size}")
                project.configurations.archives.get().allArtifacts.files.forEach { file ->
                    println("       - Artifact: ${file.name}")
                }
            }
        }
    }
}