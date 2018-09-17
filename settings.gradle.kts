enableFeaturePreview("IMPROVED_POM_SUPPORT")
rootProject.name = "chazm"

val kotlinVersion = "1.2.70"
pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("org.jetbrains.kotlin")) {
                useVersion(kotlinVersion)
            }
        }
    }
}

include("chazm-api")
include("chazm-model")
include("chazm-ui")
