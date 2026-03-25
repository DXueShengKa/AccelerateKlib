
plugins {
    id("kmp-base")
}

kotlin {

    android {
        namespace = "accelerate.utils"
    }

    sourceSets {

        commonMain {
            dependencies {
                implementation(libs.jetbrains.collection)
            }
        }

        jsCommonMain.dependencies {
            implementation(kotlinWrappers.browser)
        }
    }
}
