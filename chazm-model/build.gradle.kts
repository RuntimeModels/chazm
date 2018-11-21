import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.time.Instant

plugins {
    `java-library`
    jacoco
    distribution
    `maven-publish`
    signing
    bintray
}

group = rootProject.group
version = "${rootProject.version}.0.0"

repositories {
    jcenter()
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    api(project(":chazm-api"))

    implementation(platform(com.google.inject.`guice-bom`))
    implementation(com.google.inject.guice)
    implementation(com.google.inject.extensions.`guice-assistedinject`)
    implementation("org.slf4j:slf4j-api:+")
    implementation("javax.inject:javax.inject:1")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation(io.reactivex.rxjava2.rxjava)

    testImplementation(platform(org.junit.`junit-bom`))
    testImplementation(org.junit.jupiter.`junit-jupiter-api`)
    testImplementation(org.junit.jupiter.`junit-jupiter-params`)
    testImplementation(org.assertj.`assertj-core`)
    testImplementation(org.mockito.`mockito-core`)
//    testImplementation(org.mockito.`mockito-junit-jupiter`)

    testRuntimeOnly(org.junit.jupiter.`junit-jupiter-engine`)
}

val sourceJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets.main.get().allJava)
    manifest = tasks.jar.get().manifest
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn(tasks.javadoc)
    classifier = "javadoc"
    from(tasks.javadoc.get().destinationDir)
    manifest = tasks.jar.get().manifest
}

distributions {
    main {
        contents {
            from(tasks.jar)
            from(sourceJar)
            from(javadocJar)
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(tasks.jar.get())
            artifact(sourceJar.get())
            artifact(javadocJar.get())
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
    val moduleName = "runtimemodels.chazm.model"
    val junitModule = "org.junit.jupiter.api"

    compileJava<JavaCompile> {
        inputs.property("moduleName", moduleName)
        doFirst {
            options.compilerArgs = listOf(
                    "-Xlint", // Enables all recommended warnings.
//                    "-Werror", // Terminates compilation when warnings occur.
                    "--module-path", classpath.asPath
            )
            classpath = files()
        }
    }
    compileTestJava<JavaCompile> {
        inputs.property("moduleName", moduleName)
        doFirst {
            options.compilerArgs = listOf(
                    "-Xlint",     // Enables all recommended warnings.
                    "-Xlint:-overrides", // Disables "method overrides" warnings.
                    "-parameters", // Generates metadata for reflection on method parameters.
                    "--module-path", classpath.asPath,
                    "--add-modules", junitModule,
                    "--add-reads", "$moduleName=$junitModule",
                    "--patch-module", "$moduleName=${files(sourceSets.test.get().java.srcDirs).asPath}"
            )
            classpath = files()
        }
    }
    test<Test> {
        useJUnitPlatform()
        inputs.property("moduleName", moduleName)
        doFirst {
            jvmArgs = listOf(
                    "--module-path", classpath.asPath,
                    "--add-modules", "ALL-MODULE-PATH",
                    "--add-reads", "$moduleName=$junitModule",
                    "--add-reads", "$moduleName=org.assertj.core",
                    "--add-opens", "$moduleName/$moduleName=org.junit.platform.commons",
                    "--add-opens", "$moduleName/$moduleName.entity=org.junit.platform.commons",
                    "--add-opens", "$moduleName/$moduleName.function=org.junit.platform.commons",
                    "--add-opens", "$moduleName/$moduleName.parsers=org.junit.platform.commons",
                    "--add-opens", "$moduleName/$moduleName.parsers=org.mockito",
                    "--add-opens", "java.base/java.lang=com.google.guice",
                    "--patch-module", "$moduleName=${files(sourceSets.test.get().java.outputDir).asPath}"
            )
            classpath = files()
        }
        testLogging {
            events(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        }
    }
    jacocoTestReport<JacocoReport> {
        reports {
            csv.isEnabled = false
            xml.isEnabled = true
            html.isEnabled = System.getenv("CI").isNullOrBlank()
        }
    }
    jar<Jar> {
        manifest {
            attributes["Implementation-Title"] = project.name
            attributes["Implementation-Version"] = project.version
        }
    }
    javadoc {
        inputs.property("moduleName", moduleName)
        val options = options as CoreJavadocOptions
        doFirst {
            options.addStringOption("-module-path", classpath.asPath)
        }
    }
}
