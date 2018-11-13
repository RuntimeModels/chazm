import org.jetbrains.kotlin.Kotlin
rootProject.name = "chazm"
pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith(prefix = Kotlin.ID)) {
                useVersion(Kotlin.VERSION)
            }
        }
    }
}
include("chazm-api")
include("chazm-model")
include("chazm-ui")
