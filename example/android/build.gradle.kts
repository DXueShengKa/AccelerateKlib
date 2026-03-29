import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "accelerate.simple"
    compileSdk = libs.versions.android.compileSdk
        .get()
        .toInt()

    defaultConfig {
        applicationId = "accelerate.simple"
        minSdk = libs.versions.android.minSdk
            .get()
            .toInt()
        targetSdk = libs.versions.android.targetSdk
            .get()
            .toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    implementation(projects.example.compose)
//    debugImplementation(libs.compose.uiTooling)
}
