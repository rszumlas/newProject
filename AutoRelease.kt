import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T : Any> LifecycleOwner.autoRelease() =
    AutoReleasedProperty<T>(this)

class AutoReleasedProperty<T : Any>(lifecycleOwner: LifecycleOwner) :
    ReadWriteProperty<Any, T> {

    private var internalValue: T? = null
    private var ownerDestroyed = false

    init {
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                ownerDestroyed = true
                internalValue = null
                super.onDestroy(owner)
            }
        })
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T =
        internalValue ?: run {
            @Suppress("ReplaceGuardClauseWithFunctionCall")
            if (ownerDestroyed){
                throw IllegalStateException(
                    "Property '$property.name' is unavailable because its owning '${thisRef.javaClass.name} has been destroyed."
                )
            } else {
                throw IllegalStateException(
                    "Property '$property.name' cannot be accessed because it has not yet been set. " +
                            "This '${AutoReleasedProperty::class.java.simpleName}' is effectively a 'lateinit var'.")
            }
        }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        internalValue = value
    }

}
