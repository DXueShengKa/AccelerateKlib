plugins {
    kotlin("jvm")
}

group = "accelerate.ksp.navigation"
version = "0.0.1"

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(projects.ksp.annotation)
    implementation(libs.ksp)
//    implementation(libs.squareup.kotlinpoet.ksp)
}
