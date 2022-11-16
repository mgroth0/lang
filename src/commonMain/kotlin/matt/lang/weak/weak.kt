package matt.lang.weak

import matt.lang.delegation.provider
import matt.lang.delegation.valProp
import matt.lang.sync.inSync
import kotlin.reflect.KProperty

fun <T: Any> lazyWeak(op: ()->T) = provider {
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

fun <T: Any> lazySoft(op: ()->T) = provider {
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


expect open class WeakRef<T: Any>(value: T) {
  open fun deref(): T?
}

operator fun <R: Any, T: Any> WeakRef<T>.getValue(thisRef: R, property: KProperty<*>): T? = deref()
operator fun <R: Any, T: Any> SoftRef<T>.getValue(thisRef: R, property: KProperty<*>): T? = deref()

expect class SoftRef<T: Any>(value: T) {
  fun deref(): T?
}


class WeakPair<F: Any, S: Any>(first: F, second: S): WeakRef<Pair<F, S>>(first to second) {

  private val firstRef = WeakRef(first)
  private val secondRef = WeakRef(second)
  private val pair
	get() = firstRef.deref()?.let { f ->
	  secondRef.deref()?.let { s ->
		f to s
	  }
	}
  val first get() = pair?.first
  val second get() = pair?.second

  operator fun component1() = first
  operator fun component2() = second

  override fun deref() = pair

}