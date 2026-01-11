plugins {
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19" apply false
}

group = "ovh.roro.libraries"
version = "1.21.11"

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    group = rootProject.group.toString()
    version = rootProject.version.toString()

    repositories {
        maven {
            name = "roro"
            url = uri("https://repo.roro.ovh/repository/global/")
        }
    }

    extensions.configure<JavaPluginExtension> {
        // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))

        withJavadocJar()
        withSourcesJar()
    }

    tasks.withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
        // See https://openjdk.java.net/jeps/247 for more information.
        options.release.set(21)
    }

    tasks.withType<Javadoc> {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }

    tasks.withType<ProcessResources> {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }

    extensions.configure<PublishingExtension> {
        publications {
            create<MavenPublication>("maven") {
                artifact(tasks["jar"])
                artifact(tasks["javadocJar"]) {
                    classifier = "javadoc"
                }
                artifact(tasks["sourcesJar"]) {
                    classifier = "sources"
                }
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
}