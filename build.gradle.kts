// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version AgpVersion apply false
    id("com.android.library") version AgpVersion apply false
    id("org.jetbrains.kotlin.android") version KotlinVersion apply false
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}