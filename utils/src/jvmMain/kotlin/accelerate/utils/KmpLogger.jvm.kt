package accelerate.utils

import java.util.logging.Logger

actual class KmpLogger actual constructor(
    private val showTag: Boolean,
    tag: String,
    subtag: String?,
) {
    private val logger = Logger.getLogger(subtag?.let { "$tag $it" } ?: tag)

    actual fun info(message: String?) {
        if (showTag && message != null) logger.info(message)
    }

    actual fun debug(message: String?) {
        if (showTag && message != null) logger.fine(message)
    }

    actual fun warning(message: String?) {
        if (showTag && message != null) logger.warning(message)
    }

    actual fun error(message: String?) {
        if (showTag && message != null) logger.severe(message)
    }
}
