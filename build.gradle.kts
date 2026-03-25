
plugins {
    alias(libs.plugins.kotlin.composeCompiler) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library.kmp) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.ktlint)
}

ktlint {
    android = false

    filter {
        // 包含所有 kotlin 源并排除 build 目录
        include("**/src/**/kotlin/**")
        include("**/src/**/kotlin/**/*.kt")
        include("**/*.kts")
        exclude("**/build/**")
    }
}

dependencies {
    ktlintRuleset(libs.compose.rules.ktlint)
}

