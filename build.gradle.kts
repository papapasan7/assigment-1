plugins {
    kotlin("jvm") version "2.0.20"
    // Plugin for Dokka - KDoc generating tool
    id("org.jetbrains.dokka") version "1.9.20"
    jacoco
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
    application
}
tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    // for building a fat jar - include all dependencies
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith("jar") }
            .map { zipTree(it) }
    })
}
group = "ie.setu"
version = "4.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.thoughtworks.xstream:xstream:1.4.18")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.9.20")
}

tasks.test {
    useJUnitPlatform()

    finalizedBy(tasks.jacocoTestReport)
}
kotlin {
    jvmToolchain(16)
}
