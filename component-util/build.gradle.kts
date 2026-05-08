import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("library.paper")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = TestExceptionFormat.FULL
    }
    failFast = true
}

tasks.withType<Javadoc> {
    exclude("ovh/roro/libraries/componentutil/internal")
}