// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
//    alias(libs.plugins.android.application) apply false
//    alias(libs.plugins.kotlin.android) apply false
//    alias(libs.plugins.kotlin.compose) apply false

    alias(libs.plugins.kotlin.serialization) apply false
    kotlin("jvm") version "2.0.21"
}
//dependencies {
//    implementation(kotlin("stdlib-jdk8"))
//}
//repositories {
//    mavenCentral()
//}
//kotlin {
//    jvmToolchain(8)
//}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}
//repositories {
//    mavenCentral()
//}
kotlin {
    jvmToolchain(21)
}
