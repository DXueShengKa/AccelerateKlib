
plugins {
    id("kmp-base")
}

plugins.apply("com.google.devtools.ksp")

val pluginLibs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

kotlin {

    sourceSets {
        commonMain.dependencies {
            implementation(pluginLibs.findLibrary("koin-core").get())
            implementation(pluginLibs.findLibrary("koin-annotations").get())
        }
    }
}

dependencies {
    "ksp"(pluginLibs.findLibrary("koin-compiler").get())
}
