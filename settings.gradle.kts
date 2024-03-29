pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Keywd"
include(":app")
include(":datasource")
include(":repository")
include(":model")
include(":core")
include(":ui:core")
include(":ui:home")
include(":ui:calendar")
include(":ui:diarylist")
include(":ui:adddiary")
