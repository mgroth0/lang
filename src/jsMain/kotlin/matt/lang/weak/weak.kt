package matt.lang.weak

actual external class WeakRef<T: Any> actual constructor(value: T?) {
  actual fun deref(): T?
}

