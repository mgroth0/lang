package matt.lang.weak

actual open external class WeakRef<T: Any> actual constructor(value: T) {
  actual open fun deref(): T?
}

actual class SoftRef<T: Any> actual constructor(value: T) {
  actual fun deref(): T? {
	TODO("Not yet implemented")
  }
}