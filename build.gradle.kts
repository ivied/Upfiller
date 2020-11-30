//import org.jetbrains.kotlin.codegen.ClassBuilderFactories.binaries
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.js.translate.context.Namer.kotlin
import sun.tools.jar.resources.jar

version = "1.0-SNAPSHOT"

plugins {
    `java-library`
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

buildscript {
    var kotlin_version: String by extra
    kotlin_version = "1.4.10"

    repositories {
        mavenCentral()
    }
    
    dependencies {
        classpath(kotlin("gradle-plugin", kotlin_version))

        classpath("com.github.jengelman.gradle.plugins:shadow:6.1.0")
    }

    repositories {

        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }

    }
}



apply {
    plugin("java")
    plugin("kotlin")

    plugin("com.github.johnrengelman.shadow")
}

val kotlin_version: String by extra

repositories {
    mavenCentral()

}

dependencies {
    compile(kotlin("stdlib-jdk8", kotlin_version))
    compile("com.itextpdf:itextpdf:5.5.13.1" )
    compile( "no.tornado:tornadofx:1.7.17")
   // compile( "org.jetbrains.anko:anko:0.10.8")

    compile ("pl.allegro.finance:tradukisto:1.8.0")
    testCompile("junit", "junit", "4.12")
}
configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}


tasks.withType<Jar> {
    // Otherwise you'll get a "No main manifest attribute" error
    manifest {
        attributes["Main-Class"] = "App"
    }

    // To add all of the dependencies otherwise a "NoClassDefFoundError" error
    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}