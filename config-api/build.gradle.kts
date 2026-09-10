plugins {
    id("library.paper")
}

dependencies {
    api(project(":library-loader"))
    api(project(":common"))
}

tasks.withType<Javadoc> {
    exclude("ovh/roro/libraries/config/impl")
}