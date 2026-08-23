plugins {
    java
    application
    id("org.jetbrains.kotlin.jvm") version "2.2.10"
    id("org.javamodularity.moduleplugin") version "1.8.12"
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.beryx.jlink") version "2.25.0"
}

group = "ca.qc.cegepheritage.g30l6"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.10.2"


tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("ca.qc.cegepheritage.g30a2")
    mainClass.set("ca.qc.cegepheritage.g30a2.CafeApplication")
}
kotlin {
    jvmToolchain(24)
}

javafx {
    version = "25"
    modules = listOf("javafx.controls")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
    testImplementation(kotlin("test"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jlink {
    imageZip.set(layout.buildDirectory.file("/distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "app"
    }
}
