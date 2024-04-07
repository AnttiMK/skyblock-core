import java.io.ByteArrayOutputStream

plugins {
    java
    id("io.github.goooler.shadow") version "8.1.7"
}

group = "dev.kopo.skyblockcore"
version = "1.0.0-SNAPSHOT"
val javaVersion = 21

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(javaVersion)
    }
}

tasks.withType<JavaCompile> {
    options.release = javaVersion
    options.encoding = "UTF-8"
}

val determineCommitHash = {
    val output = ByteArrayOutputStream()
    project.exec {
        commandLine("git", "rev-parse", "--short", "HEAD")
        standardOutput = output
    }
    output.toString().trim()
}

project.extra["commitHash"] = determineCommitHash()

tasks {
    named("clean") {
        doLast {
            file("builds").deleteRecursively()
        }
    }
}

/*if (project.hasProperty("buildScan")) {
    buildScan {
        termsOfServiceUrl.set("https://gradle.com/terms-of-service")
        termsOfServiceAgree.set("yes")
    }
}*/
