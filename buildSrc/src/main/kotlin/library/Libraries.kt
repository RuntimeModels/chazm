package library

import v.guice
import v.kotlin
import v.springBoot


object kotlin {
    val jvm = "org.jetbrains.kotlin.jvm:$kotlin"
}

object springBoot {
    val bom = "org.springframework.boot:spring-boot-starter-parent:$springBoot"
}

object guice {
    val bom = "com.google.inject:guice-bom:$guice"
    val core = "com.google.inject:guice"
    val `assisted-inject` = "com.google.inject.extensions:guice-assistedinject"
}

val lombok = "org.projectlombok:lombok:${v.lombok}"

object junit {
    val bom = "org.junit:junit-bom:${v.junit}"
    object jupiter {
        val api = "org.junit.jupiter:junit-jupiter-api"
        val engine = "org.junit.jupiter:junit-jupiter-engine"
        val params = "org.junit.jupiter:junit-jupiter-params"
    }
    val vintage = "org.junit.vintage:junit-vintage-engine"
}