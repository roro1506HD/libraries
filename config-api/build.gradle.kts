plugins {
    id("library.paper")
}

dependencies {
    api(project(":library-loader"))
}

tasks.withType<Javadoc> {
    exclude("ovh/roro/libraries/config/impl")
}