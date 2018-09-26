import chazm.Dependencies

plugins {
    `build-scan`
    base
    eclipse
    idea

//    id("com.github.kt3k.coveralls") version "2.8.2"
//    id("org.sonarqube") version "2.6.2"
//    id("org.standardout.versioneye") version "1.5.0"

    chazm.Plugins.spring.dependencyManagement.add(this)
    chazm.Plugins.bintray.add(this) apply false
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

dependencyManagement {
    val javaeeVersion: String by project
    val jmockitVersion: String by project
    val junitVersion: String by project
    val lombokVersion: String by project
    dependencies {
        dependency("javax:javaee-api:$javaeeVersion")
        dependency("org.jmockit:jmockit:$jmockitVersion")
        dependency("junit:junit:$junitVersion")
        dependency("org.projectlombok:lombok:$lombokVersion")
    }

    imports {
        mavenBom("io.spring.platform:platform-bom:2.0.8.RELEASE")
        mavenBom(Dependencies.guice.bom)
    }
}

tasks {
    withType(Wrapper::class) {
        distributionType = Wrapper.DistributionType.ALL
        gradleVersion = chazm.gradle
    }
    create("printInfo") {
        doLast {
            println(" Project name: ${project.name}")
            println(" - Group Id: ${project.group}")
            println(" - Major version: ${project.version}")
            println(" - Number of subproject: ${subprojects.size}")
            subprojects.forEach {
                println("   - Subproject name: ${it.name}:")
                println("     - Group Id: ${it.group}")
                println("     - Version: ${it.version}")
                println("     - Number of archive artifacts: ${it.configurations.archives.allArtifacts.size}")
                it.configurations.archives.artifacts.files.forEach {
                    println("       - Artifact: ${it.name}")
                }
            }
        }
    }
}