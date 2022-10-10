package matt.lang.delegation

import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <V, P: ReadOnlyProperty<Any?, V>> provider(
  provideDelegate: (pName: String)->P
) = PropertyDelegateProvider { _: Any?, property -> provideDelegate(property.name) }

fun <V, P: ReadOnlyProperty<Any?, V>> fullProvider(
  provideDelegate: (thisRef: Any?, prop: KProperty<*>)->P
) = PropertyDelegateProvider { thisRef: Any?, property -> provideDelegate(thisRef, property) }

fun <V> valProp(
  op: ()->V
) = ReadOnlyProperty { _: Any?, _ -> op() }

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