pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()

    }
    plugins {
        id("com.android.application") version "8.2.2"
        id("com.google.gms.google-services") version "4.4.0"
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {url=uri("https://jitpack.io")}
        google()
        jcenter()
    }
}

rootProject.name = "assigmentFinal"
include(":app")
 