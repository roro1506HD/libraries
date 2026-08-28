import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import xyz.jpenilla.runpaper.task.RunServer

plugins {
    id("library.paper")
    id("com.gradleup.shadow") version "9.4.1"
    id("xyz.jpenilla.run-paper") version "3.1.0"
}

val generatorImplementationConfiguration: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}
sourceSets {
    main {
        java {
            srcDir("src/generated/java")
        }
    }

    create("generator") {
        java {
            srcDir("src/generator/java")
            compileClasspath += generatorImplementationConfiguration
        }

        resources {
            srcDir("src/generator/resources")
        }
    }
}

dependencies {
    api(project(":language-api"))

    generatorImplementationConfiguration("com.palantir.javapoet:javapoet:0.14.0")
    generatorImplementationConfiguration("org.jetbrains:annotations:26.0.2-1")
}

shadow {
    addShadowVariantIntoJavaComponent = false
    addShadowJarToAssembleLifecycle = false
}

paperweight.addServerDependencyTo.add(configurations["compileOnly"])
paperweight.addServerDependencyTo.add(configurations["generatorCompileOnly"])

tasks.withType<Javadoc> {
    exclude("ovh/roro/libraries/inventory/impl")
}

tasks.register<RunServer>("runGenerator") {
    dependsOn(tasks.named("generatorShadowJar"))

    group = "run paper"
    minecraftVersion("26.2")
    disablePluginRemapping()
    systemProperty("com.mojang.eula.agree", "true")

    pluginJars.setFrom(tasks.named<ShadowJar>("generatorShadowJar").get().archiveFile.get().asFile)
}

tasks.register<ShadowJar>("generatorShadowJar") {
    description = "Includes javapoet in generator jar"
    archiveBaseName = "inventory-api-generator"
    from(sourceSets["generator"].output)
    configurations = listOf(generatorImplementationConfiguration)
}

tasks.named<ProcessResources>("processGeneratorResources") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}