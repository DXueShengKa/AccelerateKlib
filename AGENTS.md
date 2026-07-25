# AccelerateKlib AI Agent Guidelines

## Architecture Overview
This is a Kotlin Multiplatform (KMP) library project with custom Gradle plugins for streamlined setup. Core modules include:
- `utils`: Common utilities with platform-specific extensions (e.g., JS uses kotlin-wrappers.browser)
- `ui/navigation`: Compose Multiplatform navigation components using JetBrains Navigation3
- `ksp/annotation`: KMP annotations library
- `ksp/navigation`: KSP processor generating navigation DSL from `@NavRoute` annotations
- `example/compose`: Multiplatform demo app showcasing library usage

## Key Patterns
- **Source Set Hierarchy**: Uses custom `hierarchyTemplate` with groups like `jsCommon` (JS + WasmJS), `jvmCommon` (JVM + Android), `native` (all native targets). Apply via `applyHierarchyTemplate(hierarchyTemplate)` in KMP blocks.
- **Plugins**: 
  - `kmp-base`: Base KMP setup with targets (JVM, Android, iOS, JS, WasmJS) and compiler args like `-Xcontext-parameters`.
  - `kmp-compose`: Extends `kmp-base`, adds JetBrains Compose and UI dependencies.
  - `kmp-koin`: Extends `kmp-base`, adds KSP and Koin dependencies for DI.
- **Namespaces**: Follow `accelerate.{module}` pattern, e.g., `accelerate.ui.navigation`.
- **Dependencies**: Managed via `libs.versions.toml` version catalog. Use `kotlinWrappers` for JS-specific libs.
- **KSP Generation**: Annotate classes with `@NavRoute` to auto-generate `appNavKeys()` and `appNavPolymorphic()` functions in package specified by `PACKAGE_NAME` option (default `navigation.gen`).

## Developer Workflows
- **Build**: `./gradlew build` compiles all modules.
- **Run Example**: `./gradlew :example:compose:run` for desktop; build iOS framework via `./gradlew :example:compose:iosArm64Binaries` and integrate in Xcode.
- **Lint**: `./gradlew ktlintCheck` enforces code style; `./gradlew ktlintFormat` auto-fixes.
- **KSP**: Processors run automatically on compile; generated code in `build/generated/ksp/`.

## Conventions
- Use `OptIn` for experimental APIs like `@ExperimentalKotlinGradlePluginApi`.
- Compose previews in `commonMain` with `@Preview`.
- Version catalog access in plugins via `extensions.getByType<VersionCatalogsExtension>().named("libs")`.

Reference: `settings.gradle.kts` for module structure, `build-plugin/src/main/kotlin/` for plugin scripts.</content>
<parameter name="filePath">D:\code\MyProject\AccelerateKlib\AGENTS.md
