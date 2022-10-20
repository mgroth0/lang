package matt.lang.delegation

import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/*
@Suppress("UnusedReceiverParameter")
fun <R, V, P: ReadOnlyProperty<R, V>> R.provider(
  provideDelegate: (pName: String)->P
) = PropertyDelegateProvider { _: R, property -> provideDelegate(property.name) }

fun <R, V, P: ReadOnlyProperty<R, V>> provider(
  provideDelegate: (pName: String)->P
) = PropertyDelegateProvider { _: R, property -> provideDelegate(property.name) }

fun <R, V, P: ReadOnlyProperty<R, V>> fullProvider(
  provideDelegate: (thisRef: R, prop: KProperty<*>)->P
) = PropertyDelegateProvider { thisRef: R, property -> provideDelegate(thisRef, property) }

fun <R, V> R.valProp(
  op: ()->V
) = ReadOnlyProperty { _: R, _ -> op() }

fun <R, V> valProp(
  op: ()->V
) = ReadOnlyProperty { _: R, _ -> op() }

fun <R, V> varProp(
  getter: ()->V,
  setter: (V)->Unit
) = object: ReadWriteProperty<R, V> {

  override fun getValue(thisRef: R, property: KProperty<*>): V {
	return getter()
  }

  override fun setValue(thisRef: R, property: KProperty<*>, value: V) {
	setter(value)
  }

}
*/


fun <R: Any, V, P: ReadOnlyProperty<R, V>> R.provider(
  provideDelegate: (pName: String)->P
) = PropertyDelegateProvider { _: R, property -> provideDelegate(property.name) }

fun <V, P: ReadOnlyProperty<Any?, V>> provider(
  provideDelegate: (pName: String)->P
) = PropertyDelegateProvider { _: Any?, property -> provideDelegate(property.name) }

fun <R, V, P: ReadOnlyProperty<R, V>> R.fullProvider(
  provideDelegate: (thisRef: R, prop: KProperty<*>)->P
) = PropertyDelegateProvider { thisRef: R, property -> provideDelegate(thisRef, property) }

fun <V, P: ReadOnlyProperty<Any?, V>> fullProvider(
  provideDelegate: (thisRef: Any?, prop: KProperty<*>)->P
) = PropertyDelegateProvider { thisRef: Any?, property -> provideDelegate(thisRef, property) }

fun <R, V> R.valProp(
  op: ()->V
) = ReadOnlyProperty { _: R, _ -> op() }

fun <R, V> valProp(
  op: ()->V
) = ReadOnlyProperty { _: R, _ -> op() }

fun <V> varProp(
  getter: ()->V,
  setter: (V)->Unit
) = object: ReadWriteProperty<Any?, V> {

  override fun getValue(thisRef: Any?, property: KProperty<*>): V {
	return getter()
  }

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
	setter(value)
  }
}

fun <T, V> lazyDelegate(getDelegate: ()->ReadOnlyProperty<T, V>) = object: LazyDelegate<T, V>() {
  override fun getDelegate() = getDelegate()
}

abstract class LazyDelegate<T, V>: ReadOnlyProperty<T, V> {
  protected abstract fun getDelegate(): ReadOnlyProperty<T, V>
  private val myDelegate by lazy {
	getDelegate()
  }

  override fun getValue(thisRef: T, property: KProperty<*>): V {
	return myDelegate.getValue(thisRef, property)
  }
}