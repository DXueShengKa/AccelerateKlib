plugins {
    id("kmp-compose")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {

    android {
        namespace = "accelerate.ui.navigation"
    }

    sourceSets {

        commonMain.dependencies {
            implementation(projects.utils)
            implementation(libs.jetbrains.navigation3.ui)
            implementation(libs.jetbrains.lifecycle.navigation3)
        }
    }
}
