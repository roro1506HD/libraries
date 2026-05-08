plugins {
    id("library.paper")
}

dependencies {
    api(project(":language-api"))
}

tasks.withType<Javadoc> {
    exclude("ovh/roro/libraries/scoreboard/impl")
}