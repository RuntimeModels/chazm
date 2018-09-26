package chazm

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependenciesSpec

const val gradle = "4.10.1"

inline fun DependencyHandler.`junit-api`(handler: (_: Any) -> Dependency?): Dependency? = handler(Dependencies.junit.jupiter.api)
inline fun DependencyHandler.`junit-bom`(handler: (_: Any) -> Dependency?): Dependency? = handler(Dependencies.junit.bom)
inline fun DependencyHandler.`junit-engine`(handler: (_: Any) -> Dependency?): Dependency? = handler(Dependencies.junit.jupiter.engine)
inline fun DependencyHandler.`junit-params`(handler: (_: Any) -> Dependency?): Dependency? = handler(Dependencies.junit.jupiter.params)
inline fun DependencyHandler.`assertj-core`(handler: (_: Any) -> Dependency?): Dependency? = handler(Dependencies.assertj.core)
inline fun DependencyHandler.`guice-bom`(handler: (_: Any) -> Dependency?): Dependency? = handler(Dependencies.guice.bom)
inline fun DependencyHandler.`guice`(handler: (_: Any) -> Dependency?): Dependency? = handler(Dependencies.guice.core)
inline fun DependencyHandler.`guice-assistedinject`(handler: (_: Any) -> Dependency?): Dependency? = handler(Dependencies.guice.extensions.`assisted-inject`)

object Plugins {
    object kotlin {
        const val version = "1.2.70"
        const val jvm = "org.jetbrains.kotlin.jvm:$version"
    }

    object spring {
        object dependencyManagement {
            const val id = "io.spring.dependency-management"
            const val version = "1.0.6.RELEASE"
            fun add(spec: PluginDependenciesSpec) = spec.id(id) version version
        }
    }

    object bintray {
        const val id = "com.jfrog.bintray"
        const val version = "1.8.4"
        fun use(spec: PluginDependenciesSpec) = spec.id(id)
        fun add(spec: PluginDependenciesSpec) = use(spec) version version
    }
}

object Dependencies {
    object springBoot {
        const val id = "org.springframework.boot"
        const val version = "2.0.5.RELEASE"
        const val bom = "$id:spring-boot-starter-parent:$version"
    }

    object guice {
        const val id = "com.google.inject"
        const val version = "4.2.0"
        const val bom = "$id:guice-bom:$version"
        const val core = "$id:guice:$version"

        object extensions {
            const val id = "${guice.id}.extensions"
            const val `assisted-inject` = "$id:guice-assistedinject"
        }
    }

    const val lombok = "org.projectlombok:lombok:1.18.2"

    object junit {
        const val id = "org.junit"
        const val version = "5.3.1"
        const val bom = "$id:junit-bom:$version"

        object jupiter {
            const val id = "${junit.id}.jupiter"
            const val api = "$id:junit-jupiter-api"
            const val engine = "$id:junit-jupiter-engine"
            const val params = "$id:junit-jupiter-params"
        }

        object vintage {
            const val id = "${junit.id}.vintage"
            const val engine = "$id:junit-vintage-engine"
        }
    }

    object assertj {
        const val id = "org.assertj"
        const val version = "3.11.1"
        const val core = "$id:assertj-core:$version"
    }
}