import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.time.Instant

plugins {
    `kotlin-jvm`()
    dokka()
    jacoco
    distribution
    `maven-publish`
    signing
    bintray(includeVersion = false)
}

group = rootProject.group
version = rootProject.version

repositories {
    jcenter()
}

dependencies {
    implementation(`kotlin-stdlib-jdk8`)

    testImplementation(platform(`junit-bom`))
    testImplementation(`junit-jupiter-api`)
    testImplementation(`junit-jupiter-params`)
    testImplementation(`assertj-core`)

    testRuntimeOnly(`junit-jupiter-engine`)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

val sourceJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets.main.get().allJava)
    manifest = tasks.jar.get().manifest
}

val dokkaJar by tasks.registering(Jar::class) {
    dependsOn(tasks.dokka)
    classifier = "dokka"
    from(tasks.dokka.get().outputDirectory)
    manifest = tasks.jar.get().manifest
}

distributions {
    main {
        contents {
            from(tasks.jar)
            from(sourceJar)
            from(dokkaJar)
        }
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifact(tasks.jar.get())
            artifact(sourceJar.get())
            artifact(dokkaJar.get())
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_KEY")
    setPublications("mavenJava")
    dryRun = System.getenv("TRAVIS_BRANCH") != "master" || System.getenv("TRAVIS_PULL_REQUEST") != "false"
    publish = System.getenv("TRAVIS_BRANCH") == "master" && System.getenv("TRAVIS_PULL_REQUEST") == "false"

    pkg.repo = "maven"
    pkg.name = "chazm"
    pkg.userOrg = "runtimemodels"
    pkg.desc = "An organizational runtime model"
    pkg.websiteUrl = "https://runtimemodels.github.io/chazm/"
    pkg.issueTrackerUrl = "https://github.com/runtimemodels/chazm/issues"
    pkg.vcsUrl = "https://github.com/RuntimeModels/chazm.git"
    pkg.setLicenses("Apache-2.0")
    pkg.githubRepo = "RuntimeModels/chazm"
    pkg.githubReleaseNotesFile = "CHANGES.md"
    pkg.version.name = "${project.version}"
    pkg.version.desc = "Chazm"
    pkg.version.released = Instant.now().toString()
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    test {
        useJUnitPlatform()
        testLogging {
            events(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        }
    }
    jacocoTestReport {
        reports {
            csv.isEnabled = false
            xml.isEnabled = true
            html.isEnabled = isCI
        }
    }
    jar {
        manifest {
            attributes["API-Title"] = project.name
            attributes["API-Version"] = project.version
        }
    }
}
