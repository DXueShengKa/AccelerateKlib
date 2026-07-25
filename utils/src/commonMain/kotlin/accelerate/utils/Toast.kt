package accelerate.utils

import kotlinx.coroutines.CoroutineExceptionHandler

expect object Toast {
    val exceptionHandler: CoroutineExceptionHandler

    fun showShort(text: String?)
}
