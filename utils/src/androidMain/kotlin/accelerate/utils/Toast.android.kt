package accelerate.utils

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.CoroutineExceptionHandler

actual object Toast {
    private var toast: Toast? = null

    fun init(context: Context) {
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
    }

    actual val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        showShort(throwable.message)
    }

    actual fun showShort(text: String?) {
        toast?.apply {
            duration = Toast.LENGTH_SHORT
            setText(text)
            show()
        }
    }
}
