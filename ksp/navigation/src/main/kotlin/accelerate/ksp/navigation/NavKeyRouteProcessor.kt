package accelerate.ksp.navigation

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.containingFile
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.validate
import java.io.OutputStreamWriter
import kotlin.sequences.forEach

const val PACKAGE_NAME = "PACKAGE_NAME"

class NavKeyRouteProcessor(
    environment: SymbolProcessorEnvironment,
) : SymbolProcessor {
    private val codeGenerator = environment.codeGenerator
    private val logger = environment.logger

    private val packageName = environment.options[PACKAGE_NAME] ?: "navigation.gen"

    private var hasGenerated = false

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (hasGenerated) {
            return emptyList()
        }
        createRoute(resolver)

        return emptyList()
    }

    private fun createRoute(resolver: Resolver) {
        // 无法处理的注解
        val notValidate = mutableListOf<KSAnnotated>()

        // 依赖关联这些文件，这些文件发生改变时触发ksp更改
        val originatingKSFiles = mutableListOf<KSFile>()

        val codeBody = StringBuilder()

        val fileName = "RouteNavKeys"

        codeBody.append(
            """
            package $packageName

            import androidx.navigation3.runtime.EntryProviderScope
            import androidx.navigation3.runtime.NavKey
            import kotlinx.serialization.modules.PolymorphicModuleBuilder
            import kotlinx.serialization.modules.subclass

            fun EntryProviderScope<NavKey>.appNavKeys(){

            """.trimIndent(),
        )

        val polymorphicBody = StringBuilder()
        polymorphicBody.append(
            """

            fun PolymorphicModuleBuilder<NavKey>.appNavPolymorphic(){

            """.trimIndent(),
        )

        resolver
            .getSymbolsWithAnnotation(NavRoute::class.java.name)
            .forEach {
                if (it.validate()) {
                    it.accept(GenerateNavDsl(logger, polymorphicBody), codeBody)
                    it.containingFile?.also(originatingKSFiles::add)
                } else {
                    notValidate.add(it)
                }
            }
        codeBody.append("\n}")
        polymorphicBody.append("\n}")
        codeBody.append(polymorphicBody)

        val file = codeGenerator.createNewFile(
            dependencies = Dependencies(aggregating = true, sources = originatingKSFiles.toTypedArray()),
            packageName = packageName,
            fileName = fileName,
        )

        OutputStreamWriter(file).use {
            it.write(codeBody.toString())
        }
        hasGenerated = true
    }
}
