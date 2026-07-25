plugins {
    id("kmp-base-nojs")
}

group = "accelerate.utils"
version = "0.0.1"

kotlin {

    android {
        namespace = "accelerate.utils"
    }

    js {
        binaries.library()
        browser()
    }

    sourceSets {

        commonMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.jetbrains.collection)
            }
        }

        jsCommonMain.dependencies {
            implementation(kotlinWrappers.browser)
        }
    }
}
