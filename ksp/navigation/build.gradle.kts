plugins {
    kotlin("jvm")
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(projects.ksp.annotation)
    implementation(libs.ksp)
    implementation(libs.squareup.kotlinpoet.ksp)
}
