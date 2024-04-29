// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.40.5")
        classpath("com.google.gms:google-services:4.4.1")
        classpath("io.realm:realm-gradle-plugin:10.15.1")
    }
}
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0-Beta5" apply false
    id("io.realm.kotlin") version "1.11.0" apply false
}

