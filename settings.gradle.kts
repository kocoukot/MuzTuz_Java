include(":premium-select-feature")


include(":level-select-feature")


include(":core-mvi")


include(":core-mvi")


include(":core")



dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

include(":app")
rootProject.name = "MuzTus"
include(":creators-feature")
include(":statistic-feature")
include(":game-level-feature")
include(":domain-layer")
