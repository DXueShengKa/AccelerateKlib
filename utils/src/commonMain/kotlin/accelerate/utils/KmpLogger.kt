package accelerate.utils

expect class KmpLogger(
    showTag: Boolean,
    tag: String,
    subtag: String? = null,
) {
    fun info(message: String?)

    fun debug(message: String?)

    fun warning(message: String?)

    fun error(message: String?)
}
