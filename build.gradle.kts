import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.io.ByteArrayOutputStream

plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.0"
}

group = "dev.kopo"
version = "1.0.0-SNAPSHOT"
var javaVersion = JavaVersion.VERSION_17

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.withType<JavaCompile> {
    options.release = javaVersion.majorVersion.toInt()
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

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
    }
    maven {
        name = "EngineHub"
        url = uri("https://maven.enginehub.org/repo/")
    }
    maven {
        name = "Citizens2"
        url = uri("https://repo.citizensnpcs.co/")
    }
    maven {
        name = "PlaceholderAPI"
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
    maven {
        name = "ProtocolLib"
        url = uri("https://repo.dmulloy2.net/repository/public/")
    }
    maven {
        name = "CodeMC"
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }
    maven {
        name = "bg-repo"
        url = uri("https://repo.bg-software.com/repository/api/")
    }
}

val daggerVersion = "2.44.2"

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly("net.luckperms:api:5.4")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.8-SNAPSHOT") {
        exclude(group = "*", module = "*")
    }
    compileOnly("net.citizensnpcs:citizens-main:2.0.30-SNAPSHOT") {
        exclude(group = "*", module = "*")
    }
    compileOnly("me.clip:placeholderapi:2.11.2")
    compileOnly("com.comphenix.protocol:ProtocolLib:5.0.0-SNAPSHOT")
    compileOnly("me.blackvein.quests:quests-core:4.7.2")
    compileOnly("com.bgsoftware:SuperiorSkyblockAPI:2022.9")

    implementation("com.google.dagger:dagger:$daggerVersion")
    annotationProcessor("com.google.dagger:dagger-compiler:$daggerVersion")

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.spongepowered:configurate-yaml:4.1.2")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.1.0") {
        exclude(group = "com.github.waffle", module = "waffle-jna")
    }
}

tasks {
    named("clean") {
        doLast {
            file("builds").deleteRecursively()
        }
    }
}

tasks.withType<ShadowJar> {
    val prefix = "dev.kopo.skyblockcore.lib"
    val relocations = listOf(
        "com.google.gson",
        "dagger",
        "javax.inject",
        "org.spongepowered",
        "org.yaml",
        "io.leangen",
        "com.zaxxer",
        "org.mariadb.jdbc",
        "com.github",
        "org.slf4j"
    )

    for (pkg in relocations) {
        relocate(pkg, "$prefix.$pkg")
    }

    destinationDirectory.set(file("$rootDir/builds/"))
    archiveBaseName.set("SkyblockCore")
    archiveClassifier.set("")
}

/*if (project.hasProperty("buildScan")) {
    buildScan {
        termsOfServiceUrl.set("https://gradle.com/terms-of-service")
        termsOfServiceAgree.set("yes")
    }
}*/
