pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "libraries"

sequenceOf(
    "library-loader",
    "reflection-util",
    "packet-listener",
    "component-util",
    "language-api",
    "inventory-api"
).forEach {
    include(it)
}