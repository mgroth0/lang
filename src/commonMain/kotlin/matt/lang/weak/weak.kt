package matt.lang.weak

import kotlin.reflect.KProperty

expect class WeakRef<T: Any>(value: T) {
  fun deref(): T?
}

operator fun <R: Any, T: Any> WeakRef<T>.getValue(thisRef: R, property: KProperty<*>): T? = deref()