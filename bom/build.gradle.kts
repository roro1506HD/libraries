plugins {
    `java-platform`
    `maven-publish`
}

group = rootProject.group.toString()
version = rootProject.version.toString()

dependencies {
    constraints {
        sequenceOf(
            "library-loader",
            "reflection-util",
            "packet-listener",
            "component-util",
            "language-api",
            "inventory-api",
            "config-api",
            "scoreboard-api"
        ).forEach {
            api(project(":${it}"))
        }
    }
}

extensions.configure<PublishingExtension> {
    publications {
        create<MavenPublication>("maven") {
            from(components["javaPlatform"])
        }
    }

    repositories {
        maven {
            name = "roro"
            url = uri("https://repo.roro.ovh/repository/libraries/")

            credentials(PasswordCredentials::class)
        }
    }
}