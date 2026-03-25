package accelerate.ksp.navigation

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class NavRoute(
    val navKey: KClass<*> = String::class,
    val route: String = "",
)
