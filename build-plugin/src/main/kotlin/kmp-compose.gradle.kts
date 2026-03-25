
plugins {
    id("kmp-base")
}

plugins.apply("org.jetbrains.kotlin.plugin.compose")
plugins.apply("org.jetbrains.compose")


val pluginLibs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

kotlin {

    sourceSets {

        commonMain.dependencies {
            implementation(pluginLibs.findLibrary("jetbrains-ui").get())
        }
    }
}
