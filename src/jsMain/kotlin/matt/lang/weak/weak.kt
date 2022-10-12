package matt.lang.weak

actual external class WeakRef<T: Any> actual constructor(value: T) {
  actual fun deref(): T?
}

actual class SoftRef<T: Any> actual constructor(value: T) {
  actual fun deref(): T? {
	TODO("Not yet implemented")
  }
}