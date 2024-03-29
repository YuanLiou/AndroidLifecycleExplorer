// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version AgpVersion apply false
    id("com.android.library") version AgpVersion apply false
    id("org.jetbrains.kotlin.android") version KotlinVersion apply false
    id("org.jmailen.kotlinter") version KtlinterVersion apply false
}

buildscript {
    dependencies {
        classpath("com.twitter.compose.rules:ktlint:$ComposeLintVersion")
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}