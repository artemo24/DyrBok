plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)

    kotlin("jvm") version "2.0.21"
    // kotlin("plugin.serialization") version "2.0.0"
    alias(libs.plugins.kotlin.serialization)
}

//android {
//    namespace = "com.github.artemo24.backupandrestore"
//    compileSdk = 34
//
//    defaultConfig {
//        applicationId = "com.github.artemo24.backupandrestore"
//        minSdk = 24
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//    kotlinOptions {
//        jvmTarget = "11"
//    }
//}

val firestoreVersion = "3.29.0"
val jsonSerializationVersion = "1.7.1"

dependencies {
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)

    implementation(libs.google.cloud.firestore)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
}

kotlin {
    jvmToolchain(21)
}
