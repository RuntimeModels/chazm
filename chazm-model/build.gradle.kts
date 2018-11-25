import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
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

repositories {
    jcenter()
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

group = rootProject.group
version = "${rootProject.version}.0.0"

dependencies {
    api(project(":chazm-api"))

    implementation(org.jetbrains.kotlin.`kotlin-stdlib-jdk8`)
    implementation(platform(com.google.inject.`guice-bom`))
    implementation(com.google.inject.guice)
    implementation(com.google.inject.extensions.`guice-assistedinject`)
    implementation(org.slf4j.`slf4j-api`)
    implementation(javax.inject.javax_inject)
    implementation(javax.validation.`validation-api`)
    implementation(io.reactivex.rxjava2.rxjava)

    testImplementation(org.jetbrains.kotlin.`kotlin-test-junit5`)
    testImplementation(platform(org.junit.`junit-bom`))
    testImplementation(org.junit.jupiter.`junit-jupiter-api`)
    testImplementation(org.junit.jupiter.`junit-jupiter-params`)
    testImplementation(org.assertj.`assertj-core`)
    testImplementation(org.mockito.`mockito-core`)
    testImplementation(org.mockito.`mockito-junit-jupiter`)

    testRuntimeOnly(org.junit.jupiter.`junit-jupiter-engine`)
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
        create<MavenPublication>("mavenJava") {
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
            html.isEnabled = System.getenv("CI").isNullOrBlank()
        }
    }
    jar {
        manifest {
            attributes["Implementation-Title"] = project.name
            attributes["Implementation-Version"] = project.version
        }
    }
}
