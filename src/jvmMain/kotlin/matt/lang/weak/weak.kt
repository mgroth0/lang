package matt.lang.weak

import java.lang.ref.WeakReference

actual class WeakRef<T: Any> actual constructor(value: T) {
  private val weakRef: WeakReference<T> = WeakReference(value)
  actual fun deref(): T? = weakRef.get()
}