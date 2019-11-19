import org.jetbrains.kotlin.codegen.ClassBuilderFactories.binaries
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
    kotlin_version = "1.2.10"

    repositories {
        mavenCentral()
    }
    
    dependencies {
        classpath(kotlinModule("gradle-plugin", kotlin_version))
    }
    
}

apply {
    plugin("java")
    plugin("kotlin")
}

val kotlin_version: String by extra

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlinModule("stdlib-jdk8", kotlin_version))
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


