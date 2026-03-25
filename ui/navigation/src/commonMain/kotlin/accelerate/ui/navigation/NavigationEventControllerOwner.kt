package accelerate.ui.navigation

import androidx.navigationevent.NavigationEventDispatcher
import androidx.navigationevent.NavigationEventDispatcherOwner

class NavigationEventControllerOwner<T : Any>(
    private val navigationEvent: NavigationEventDispatcher?,
    val controller: NavDisplayController<T>,
) : NavigationEventDispatcherOwner {
    val subrouteStrategy = SubrouteSceneStrategy<T>()

    private var _navigationEvent: NavigationEventDispatcher? = null

    override val navigationEventDispatcher: NavigationEventDispatcher
        get() =
            navigationEvent ?: _navigationEvent ?: NavigationEventDispatcher(
                onBackCompletedFallback = controller.onBack,
            ).also { _navigationEvent = it }
}
