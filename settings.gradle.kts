pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith(prefix = Kotlin.GROUP)) {
                useVersion(Kotlin.VERSION)
            }
        }
    }
}

rootProject.name = "chazm"

include("chazm-api")
include("chazm-model")
include("chazm-ui")
