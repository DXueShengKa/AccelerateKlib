@file:OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    id("com.android.kotlin.multiplatform.library")
}

kotlin {
    applyHierarchyTemplate(hierarchyTemplate)

    jvmToolchain(21)

    jvm()

    android {
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

    compilerOptions {
        optIn.addAll(
            "kotlin.time.ExperimentalTime",
        )

        freeCompilerArgs.addAll(
            "-Xcontext-parameters",
            "-Xexpect-actual-classes",
//            报错
//            "-Xexplicit-backing-fields",
        )
    }
}
