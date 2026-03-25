@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.android.library.kmp)
}

kotlin {
    jvm()
    android {
        namespace = "ksp.annotation"
        compileSdk { version = release(36) }
    }
    iosArm64()
    iosSimulatorArm64()
    wasmJs {
        binaries.library()
        browser()
    }
    js {
        binaries.library()
        browser()
    }
}
