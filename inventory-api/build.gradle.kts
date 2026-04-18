plugins {
    id("io.papermc.paperweight.userdev")
}

dependencies {
    paperweight.paperDevBundle("1.21.11-R0.1-SNAPSHOT")

    api(project(":library-loader"))
    api(project(":language-api"))
}

tasks.withType<Javadoc> {
    exclude("ovh/roro/libraries/inventory/impl")
}