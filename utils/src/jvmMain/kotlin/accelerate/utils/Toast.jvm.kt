package accelerate.utils

import kotlinx.coroutines.CoroutineExceptionHandler

actual object Toast {
    actual val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        showShort(throwable.message)
    }

    actual fun showShort(text: String?) {
    }
}
