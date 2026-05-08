# roro's libraries

This repository contains a collection of libraries I use in my personal projects.
Feel free to use them in your projects if you want!

I have a bad habit of making breaking changes, but will try to keep a "one major minecraft version" deprecation notice
on such changes so that you have one entire Minecraft release to make changes.

These libraries are made for latest Minecraft version only, currently 26.1.2, and only for the PaperMC software.
They are expected to be updated near the time PaperMC releases a stable build.

> [!WARNING]
> External documentation and javadocs are still WIP, not every README is finished and a lot of javadocs is missing, use with caution!

## What libraries are there?

A couple of libraries are available, each have their own README with the information on how to get started:
- **[component-util](component-util/README.md)**: Utilities when working with components, both adventure and vanilla
- **[config-api](config-api/README.md)**: API to create, load and save JSON config files, based on Java records
- **[inventory-api](inventory-api/README.md)**: API to create chest-based inventories, with many built-in features
- **[language-api](language-api/README.md)**: API to load multiple languages and their translations based on MiniMessage
- **[packet-listener](packet-listener/README.md)**: Utility to listern to incoming and outgoing packets (No wrappers, raw vanilla packets)
- **[reflection-util](reflection-util/README.md)**: Utilities to work with reflection, mainly around getting and setting fields
- **[scoreboard-api](scoreboard-api/README.md)**: API to create scoreboards, with `language-api` and MiniMessage support

The [library loader](library-loader/README.md) is an internal API for other libraries to use, thus not being listed above.

## How to use?

### Gradle

> [!TIP]
> You probably want to use the [shadow plugin](https://gradleup.com/shadow/getting-started/) to include the libraries in the final jar, and don't forget to relocate if you have multiple plugins shading the same libraries!

```kotlin
repositories {
    maven {
        url = uri("https://repo.roro.ovh/repository/global/")
    }
}

dependencies {
    // Use a BOM so that all libraries are on the same version
    implementation(platform("ovh.roro.libraries:bom:26.1.2"))
    implementation("ovh.roro.libraries:config-api")
    implementation("ovh.roro.libraries:reflection-util")
    // ...
}
```

### Maven

> [!TIP]
> You probably want to use the [maven shade plugin](https://maven.apache.org/plugins/maven-shade-plugin/usage.html) to include the libraries in the final jar, and don't forget to relocate if you have multiple plugins shading the same libraries!

```xml
<repositories>
    <repository>
        <id>roro-ovh</id>
        <url>https://repo.roro.ovh/repository/global/</url>
    </repository>
</repositories>

<!-- Use a BOM so that all libraries are on the same version -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>ovh.roro.libraries</groupId>
            <artifactId>bom</artifactId>
            <version>26.1.2</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <dependency>
        <groupId>ovh.roro.libraries</groupId>
        <artifactId>config-api</artifactId>
        <scope>compile</scope>
    </dependency>
    <dependency>
        <groupId>ovh.roro.libraries</groupId>
        <artifactId>reflection-util</artifactId>
        <scope>compile</scope>
    </dependency>
    <!-- ... -->
</dependencies>
```