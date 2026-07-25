package accelerate.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.CoroutineExceptionHandler
import platform.CoreGraphics.CGRectMake
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.UIColor
import platform.UIKit.UIFont
import platform.UIKit.UILabel
import platform.UIKit.UIView
import platform.UIKit.UIViewAnimationOptionCurveEaseIn
import platform.UIKit.UIViewAnimationOptionCurveEaseOut
import platform.UIKit.UIViewController

actual object Toast {
    private var uiViewController: UIViewController? = null

    fun init(uiViewController: UIViewController?) {
        this.uiViewController = uiViewController
    }

    actual val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        showShort(throwable.message)
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun showShort(text: String?) {
        val view = uiViewController?.view ?: return

        val label = UILabel(
            frame = view.frame.useContents {
                CGRectMake((size.width - 150) / 2, size.height * 0.8, 150.0, 40.0)
            },
        )

        label.font = UIFont.systemFontOfSize(14.0)
        label.text = text
        label.textColor = UIColor.whiteColor
        label.textAlignment = NSTextAlignmentCenter
        label.alpha = 0.1
        label.backgroundColor = UIColor.blackColor.colorWithAlphaComponent(0.5)
        label.layer.cornerRadius = 10.0
        label.clipsToBounds = true

        view.addSubview(label)

        val remove: (Boolean) -> Unit = {
            UIView.animateWithDuration(
                duration = 1.0,
                delay = 2.0,
                options = UIViewAnimationOptionCurveEaseOut,
                animations = {
                    label.alpha = 0.0
                },
                completion = {
                    label.removeFromSuperview()
                },
            )
        }

        UIView.animateWithDuration(
            duration = 1.0,
            delay = 0.1,
            options = UIViewAnimationOptionCurveEaseIn,
            animations = {
                label.alpha = 1.0
            },
            completion = remove,
        )
    }
}
