package accelerate.ksp.navigation

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.visitor.KSEmptyVisitor

class GenerateNavDsl(
    private val logger: KSPLogger,
    private val polymorphicBody: StringBuilder,
) : KSEmptyVisitor<StringBuilder, Unit>() {
    override fun defaultHandler(
        node: KSNode,
        data: StringBuilder,
    ) {
    }

    override fun visitPropertyDeclaration(
        property: KSPropertyDeclaration,
        data: StringBuilder,
    ) {
        val navRoute = property.annotations.first { it.shortName.getShortName() == NavRoute::class.simpleName }
        var routeType: KSType? = null
        var routeStr = ""

        navRoute.arguments.forEach { va ->
            when (va.name?.asString()) {
                "navKey" -> {
                    routeType = va.value as? KSType
                }

                "route" -> {
                    va.value?.also {
                        routeStr = it as String
                    }
                }
            }
        }

        if (routeType != null) {
            val typeName = routeType.declaration.qualifiedName?.asString()
            polymorphicBody.append("\n\tsubclass($typeName::class)\n")
            property.qualifiedName?.asString()?.also {
                val a = "\n\taddEntryProvider($typeName, content = $it)\n"
                data.append(a)
            }
        }
    }

    override fun visitFunctionDeclaration(
        function: KSFunctionDeclaration,
        data: StringBuilder,
    ) {
    }
}
