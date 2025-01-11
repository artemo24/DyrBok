// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // This is necessary to avoid the plugins to be loaded multiple times in each subproject's classloader.
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false

//    alias(libs.plugins.android.application) apply false
//    alias(libs.plugins.kotlin.android) apply false
//    alias(libs.plugins.kotlin.compose) apply false

    alias(libs.plugins.kotlin.serialization) apply false

    kotlin("jvm") version "2.1.0"
}

// dependencies {
//     implementation(kotlin("stdlib-jdk8"))
// }

// kotlin {
//     jvmToolchain(21)
// }
