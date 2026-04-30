plugins {
    id("library.paper")
}

dependencies {
    paperweight.paperDevBundle("1.21.11-R0.1-SNAPSHOT")

    api(project(":library-loader"))
}

tasks.withType<Javadoc> {
    exclude("ovh/roro/libraries/config/impl")
}