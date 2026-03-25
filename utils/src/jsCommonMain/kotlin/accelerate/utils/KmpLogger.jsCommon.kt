package accelerate.utils

import web.console.console

actual class KmpLogger actual constructor(
    private val showTag: Boolean,
    tag: String,
    subtag: String?,
) {
    private val tag = if (subtag == null) tag else "$tag:$subtag"

    actual fun info(message: String?) {
        if (showTag && message != null) {
            console.info(tag, message)
        }
    }

    actual fun debug(message: String?) {
        if (showTag && message != null) {
            console.debug(tag, message)
        }
    }

    actual fun warning(message: String?) {
        if (showTag && message != null) {
            console.warn(tag, message)
        }
    }

    actual fun error(message: String?) {
        if (showTag && message != null) {
            console.error(tag, message)
        }
    }
}
