package accelerate.utils

import android.util.Log

actual class KmpLogger actual constructor(
    private val showTag: Boolean,
    tag: String,
    subtag: String?,
) {
    private val tag: String = subtag?.let { "$tag $it" } ?: tag

    actual fun info(message: String?) {
        if (showTag && message != null) {
            Log.i(tag, message)
        }
    }

    actual fun debug(message: String?) {
        if (showTag && message != null) {
            Log.d(tag, message)
        }
    }

    actual fun warning(message: String?) {
        if (showTag && message != null) {
            Log.w(tag, message)
        }
    }

    actual fun error(message: String?) {
        if (showTag && message != null) {
            Log.e(tag, message)
        }
    }
}
