import buildSrc.Plugins.Kotlin

enableFeaturePreview("IMPROVED_POM_SUPPORT")
enableFeaturePreview("STABLE_PUBLISHING")
rootProject.name = "chazm"
pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("org.jetbrains.kotlin")) {
                useVersion(Kotlin.version)
            }
        }
    }
}
include("chazm-api")
include("chazm-model")
include("chazm-ui")
