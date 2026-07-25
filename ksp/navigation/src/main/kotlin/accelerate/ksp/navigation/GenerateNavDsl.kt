package accelerate.ksp.navigation

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.Modifier
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
            val routeDeclaration = routeType.declaration as KSClassDeclaration
            val typeName = routeDeclaration.qualifiedName?.asString()
            polymorphicBody.append("\tsubclass($typeName::class)\n")

            val param1 = if (routeDeclaration.classKind == ClassKind.OBJECT) {
                "key = $typeName"
            } else {
                "clazz = $typeName::class"
            }
            property.qualifiedName?.asString()?.also {
                val a = "\taddEntryProvider($param1, content = $it)\n"
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
