import java.time.Instant

plugins {
    `java-library`
    jacoco
    `maven-publish`
    signing
    bintray
}

repositories {
    jcenter()
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_10
    targetCompatibility = JavaVersion.VERSION_1_10
}

group = rootProject.group
version = "${rootProject.version}.0.0"

dependencies {
    implementation(project(":chazm-api"))
    implementation(platform(com.google.inject.`guice-bom`))
    implementation(com.google.inject.guice)
    implementation(com.google.inject.extensions.`guice-assistedinject`)
    implementation("org.slf4j:slf4j-api:+")
    implementation("javax.inject:javax.inject:1")
    implementation("javax.validation:validation-api:2.0.1.Final")

    testImplementation(junit.junit)
    testImplementation(platform(org.junit.`junit-bom`))
    testImplementation(org.junit.jupiter.`junit-jupiter-api`)
    testImplementation(org.junit.jupiter.`junit-jupiter-params`)
    testImplementation(org.jmockit.jmockit)
    testImplementation(org.assertj.`assertj-core`)
    testImplementation(org.mockito.`mockito-core`)

    testRuntimeOnly(org.junit.jupiter.`junit-jupiter-engine`)
}

val sourceJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].allJava)
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourceJar.get())
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
    val junit = "org.junit.jupiter.api"

    compileJava<JavaCompile> {
        inputs.property("moduleName", moduleName)
        doFirst {
            options.compilerArgs = listOf("--module-path", classpath.asPath)
            classpath = files()
        }
    }
    compileTestJava<JavaCompile> {
        inputs.property("moduleName", moduleName)
        doFirst {
            options.compilerArgs = listOf(
                    "--module-path", classpath.asPath,
                    "--add-modules", junit,
//                    "--add-modules", "junit",
                    "--add-reads", "$moduleName=$junit",
//                    "--add-reads", "$moduleName=junit",
                    "--patch-module", "$moduleName=" + files(sourceSets.test.get().java.srcDirs).asPath
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
                    "--add-reads", "$moduleName=$junit",
                    "--add-reads", "$moduleName=org.assertj.core",
                    "--add-opens", "$moduleName/$moduleName=org.junit.platform.commons",
                    "--add-opens", "java.base/java.lang=com.google.guice",
                    "--patch-module", "$moduleName=${files(sourceSets.test.get().java.outputDir).asPath}"
            )
            classpath = files()
        }
    }
    jacocoTestReport<JacocoReport> {
        reports {
            csv.isEnabled = false
            xml.isEnabled = true
            html.isEnabled = System.getenv("CI").isNullOrBlank()
        }
    }
}
