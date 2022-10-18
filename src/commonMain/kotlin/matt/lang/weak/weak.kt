package matt.lang.weak

import matt.lang.delegation.provider
import matt.lang.delegation.valProp
import matt.lang.sync.inSync
import kotlin.reflect.KProperty

fun <R, T: Any> lazyWeak(op: ()->T) = provider<R, _, _> {
  var ref: WeakRef<T>? = null
  val monitor = object {}
  valProp {
	inSync(monitor) {
	  ref?.deref() ?: op().also {
		ref = WeakRef(it)
	  }
	}
  }
}

fun <R, T: Any> lazySoft(op: ()->T) = provider<R, _, _> {
  var ref: SoftRef<T>? = null
  val monitor = object {}
  valProp {
	inSync(monitor) {
	  ref?.deref() ?: op().also {
		ref = SoftRef(it)
	  }
	}
  }
}


expect class WeakRef<T: Any>(value: T) {
  fun deref(): T?
}

operator fun <R: Any, T: Any> WeakRef<T>.getValue(thisRef: R, property: KProperty<*>): T? = deref()
operator fun <R: Any, T: Any> SoftRef<T>.getValue(thisRef: R, property: KProperty<*>): T? = deref()

expect class SoftRef<T: Any>(value: T) {
  fun deref(): T?
}
