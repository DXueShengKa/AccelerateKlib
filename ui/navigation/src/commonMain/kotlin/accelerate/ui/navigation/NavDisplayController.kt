package accelerate.ui.navigation

import accelerate.utils.swap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

@Stable
class NavDisplayController<K>(
    private val backStack: SnapshotStateList<K>,
) {
    companion object {
        @Suppress("UNCHECKED_CAST")
        @JvmStatic
        @Composable
        fun <T : Any> current(): NavDisplayController<T> = (LocalNavigationEventDispatcherOwner.current as NavigationEventControllerOwner<T>).controller
    }

    fun getBackStack(): List<K> = backStack

    /**
     * 固定key，一般不弹出
     */
    private val fixedKey = hashSetOf<K>()

    val onBack: () -> Unit = ::popBackStack

    val previous: K? get() = backStack.getOrNull(backStack.lastIndex - 1)

    val current: K? get() = backStack.lastOrNull()

    operator fun contains(navKey: K): Boolean = backStack.contains(navKey)

    /**
     * 弹出最后一个key
     */
    fun popBackStack(): Boolean {
        val last = backStack.last()
        if (backStack.size <= 1) return false

        if (last !in fixedKey) {
            backStackRemove(index = backStack.lastIndex)
            return true
        }

        backStackRemove(index = backStack.indexOfLast { it !in fixedKey })
        return true
    }

    /**
     * 弹出从最后到key之前的栈
     * @param inclusive true：弹出部分包括key自己
     */
    fun popBackStack(
        key: K,
        inclusive: Boolean = false,
    ): Boolean {
        if (key in fixedKey) return false
        val i = backStack.indexOf(key)
        if (i <= 1) return false
        val fromIndex = if (inclusive) i else i + 1
        val toIndex = backStack.lastIndex
        backStackRemoveRange(fromIndex, toIndex)
        return true
    }

    /**
     * 弹出符合[predicate]结果的key
     */
    fun popBackStack(predicate: (K) -> Boolean): Boolean {
        val removeList = backStack.filter(predicate)
        val b = backStack.removeAll(removeList)
        return b
    }

    /**
     * 导航到固定key,不存在则创建，存在则移动到最前，固定键不会被[popBackStack]弹出
     *
     */
    fun navigateFixed(key: K) {
        if (key !in fixedKey) {
            fixedKey.add(key)
        }
        navigateTop(key)
    }

    /**
     * 导航到key
     * @param top false：把key添加到最后一列，true：如果key存在移动到最后，不存在则直接添加
     * @param removeFrontAll 移除key之前所有
     */
    @JvmOverloads
    fun navigate(
        key: K,
        top: Boolean = false,
        removeFrontAll: Boolean = false,
    ) {
        if (top) {
            navigateTop(key)
        } else {
            backStack.add(key)
        }
        if (removeFrontAll) {
            fixedKey.clear()
            backStackRemoveRange(0, backStack.lastIndex - 1)
        }
    }

    private fun backStackRemove(index: Int) {
        backStack.removeAt(index)
    }

    private fun backStackRemoveRange(
        fromIndex: Int,
        toIndex: Int,
    ) {
        backStack.removeRange(fromIndex, toIndex)
    }

    private fun navigateTop(key: K) {
        val i = backStack.indexOf(key)
        if (i < 0) {
            backStack.add(key)
        } else if (i != backStack.lastIndex) {
            backStack.swap(i, backStack.lastIndex)
        }
    }
}

@Composable
fun rememberNavDisplayControllerOwner(
    configuration: SavedStateConfiguration,
    vararg navKeys: NavKey,
): NavigationEventControllerOwner<NavKey> {
    val ndc = rememberSerializable(
        configuration = configuration,
        serializer = NavDisplayControllerSerializer,
    ) {
        val backStack = mutableStateListOf<NavKey>()
        backStack.addAll(navKeys.toList())
        NavDisplayController(
            backStack = backStack,
        )
    }
    val parent = LocalNavigationEventDispatcherOwner.current
    return remember(ndc) {
        NavigationEventControllerOwner(
            parent?.navigationEventDispatcher,
            ndc,
        )
    }
}

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
private object NavDisplayControllerSerializer : KSerializer<NavDisplayController<NavKey>> {
    private val listSerializer = ListSerializer(PolymorphicSerializer(NavKey::class))

    override val descriptor: SerialDescriptor =
        SerialDescriptor(
            "NavDisplayController",
            listSerializer.descriptor,
        )

    override fun deserialize(decoder: Decoder): NavDisplayController<NavKey> {
        val backStack = mutableStateListOf<NavKey>()
        backStack.addAll(
            decoder.decodeSerializableValue(listSerializer),
        )
        return NavDisplayController(
            backStack = backStack,
        )
    }

    @OptIn(InternalSerializationApi::class)
    override fun serialize(
        encoder: Encoder,
        value: NavDisplayController<NavKey>,
    ) {
        encoder.encodeSerializableValue(serializer = listSerializer, value = value.getBackStack())
    }
}
