
plugins {
    `kotlin-dsl`
}

group = "build-plugin"

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(libs.android.gradle)
    implementation(libs.kotlin.gradle)
}
