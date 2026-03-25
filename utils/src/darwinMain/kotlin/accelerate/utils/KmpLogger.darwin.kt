package accelerate.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ptr
import platform.darwin.OS_LOG_TYPE_DEBUG
import platform.darwin.OS_LOG_TYPE_ERROR
import platform.darwin.OS_LOG_TYPE_FAULT
import platform.darwin.OS_LOG_TYPE_INFO
import platform.darwin.__dso_handle
import platform.darwin._os_log_internal
import platform.darwin.os_log_create

@OptIn(ExperimentalForeignApi::class)
actual class KmpLogger actual constructor(
    private val showTag: Boolean,
    tag: String,
    subtag: String?,
) {
    private val log = os_log_create(subsystem = tag, category = subtag)

    actual fun info(message: String?) {
        if (showTag && message != null) {
            _os_log_internal(__dso_handle.ptr, log, OS_LOG_TYPE_INFO, message)
        }
    }

    actual fun debug(message: String?) {
        if (showTag && message != null) {
            _os_log_internal(__dso_handle.ptr, log, OS_LOG_TYPE_DEBUG, message)
        }
    }

    actual fun warning(message: String?) {
        if (showTag && message != null) {
            _os_log_internal(__dso_handle.ptr, log, OS_LOG_TYPE_FAULT, message)
        }
    }

    actual fun error(message: String?) {
        if (showTag && message != null) {
            _os_log_internal(__dso_handle.ptr, log, OS_LOG_TYPE_ERROR, message)
        }
    }
}
