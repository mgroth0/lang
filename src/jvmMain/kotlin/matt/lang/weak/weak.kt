package matt.lang.weak

import java.lang.ref.SoftReference
import java.lang.ref.WeakReference

actual class WeakRef<T: Any> actual constructor(value: T) {
  private val weakRef: WeakReference<T> = WeakReference(value)
  actual fun deref() = weakRef.get()
}


actual class SoftRef<T: Any> actual constructor(value: T) {
  private val softRef: SoftReference<T> = SoftReference(value)
  actual fun deref() = softRef.get()
}